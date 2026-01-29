package com.bookservice.coupon.dto.request;

import lombok.Getter;

@Getter
public class CouponUpdateRequest {
	private String name;
	private Integer amount;
	private Integer discountValue;
}
