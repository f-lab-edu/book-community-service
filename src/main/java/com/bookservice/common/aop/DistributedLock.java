package com.bookservice.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

	/**
	 * 락 이름
	 */
	String key();

	/**
	 * 락의 시간 단위
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/**
	 * 락을 기다리는 시간 - default 5s(5초)
	 * 락 획득을 위해 waitTime만큼 대기한다.
	 */
	long timeWait() default 5L;

	/**
	 * 락 임대 시간 - default 3s(3초)
	 *. 락을 획득한 이후 설정한 leaseTime 이후 락이 해제된다.
	 */
	long leaseTime() default 3L;
}
