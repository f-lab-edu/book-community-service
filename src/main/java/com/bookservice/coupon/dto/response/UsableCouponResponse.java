package com.bookservice.coupon.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UsableCouponResponse {
	private Long couponId;
	private String name;
	private String couponStatus;
	private LocalDateTime issuedAt;
	private LocalDateTime endDate;
	private Long discountValue;
	private LocalDateTime createdAt;
}
