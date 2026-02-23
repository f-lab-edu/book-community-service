package com.bookservice.common.aop;

import com.bookservice.common.utils.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAOP {
	private static final String REDISSON_LOCK_PREFIX = "LOCK:";

	private final RedissonClient redissonClient;
	private final AopForTransaction aopForTransaction;
	private final CacheManager cacheManager;

	@Around("@annotation(com.bookservice.common.aop.DistributedLock)")
	public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
				signature.getParameterNames(),
				joinPoint.getArgs(),
				distributedLock.key()
		);
		RLock lock = redissonClient.getLock(key);

		try {
			boolean available = lock.tryLock(distributedLock.timeWait(), distributedLock.leaseTime(), distributedLock.timeUnit());
			if(!available){
				throw new RuntimeException("락 획득에 실패 하였습니다.");
			}
			return aopForTransaction.proceed(joinPoint);
		} catch (InterruptedException e) {
			throw new InterruptedException();
		} finally {
			try {
				lock.unlock();
			} catch (IllegalMonitorStateException e){
				log.info("레디슨 락을 해제해주세요. serviceName = {}, key = {}",
						method.getName(),
						key
				);
			}
		}
	}

	@Around("@annotation(com.bookservice.common.aop.DistributedCacheable)")
	public Object handleCacheStampede(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedCacheable distributedCacheable = method.getAnnotation(DistributedCacheable.class);

		String cacheAbleKey = CustomSpringELParser.getDynamicValue(
				signature.getParameterNames(),
				joinPoint.getArgs(),
				distributedCacheable.key()
		).toString();

		String cacheName = distributedCacheable.cacheName();
		Cache cache = cacheManager.getCache(cacheName);

		if (cache != null) {
			Cache.ValueWrapper wrapper = cache.get(cacheAbleKey);
			if (wrapper != null) {
				return wrapper.get();
			}
		}

		String lockKey = REDISSON_LOCK_PREFIX + cacheAbleKey;
		RLock lock = redissonClient.getLock(lockKey);

		try {
			boolean available = lock.tryLock(distributedCacheable.timeWait(), distributedCacheable.leaseTime(), distributedCacheable.timeUnit());
			if (!available) {
				throw new RuntimeException("캐시 갱신을 위한 락 획득에 실패하였습니다.");
			}

			if (cache != null) {
				Cache.ValueWrapper wrapper = cache.get(cacheAbleKey);
				if (wrapper != null) {
					return wrapper.get();
				}
			}

			Object dbResult = joinPoint.proceed();

			if (cache != null && dbResult != null) {
				cache.put(cacheAbleKey, dbResult);
			}

			return dbResult;

		} catch (InterruptedException e) {
			throw new InterruptedException();
		} finally {
			try {
				if (lock.isLocked() && lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			} catch (IllegalMonitorStateException e) {
				log.info("레디슨 락을 해제해주세요. serviceName = {}, key = {}", method.getName(), lockKey);
			}
		}
	}
}
