package com.bookservice.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedCacheable {
	/**
	 * 캐시 공간 이름 (예: "weeklyBestSellers")
	 */
	String cacheName();

	/**
	 * 캐시 키 (spEL 지원)
	 */
	String key();

	/**
	 * 락의 시간 단위
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/**
	 * 락을 기다리는 시간 - default 15s(15초)
	 * 락 획득을 위해 waitTime만큼 대기한다.
	 */
	long timeWait() default 15L;

	/**
	 * 락 임대 시간 - default 3s(3초)
	 *. 락을 획득한 이후 설정한 leaseTime 이후 락이 해제된다.
	 */
	long leaseTime() default 1L;
}
