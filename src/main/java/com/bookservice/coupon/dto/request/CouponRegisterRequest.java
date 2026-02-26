package com.bookservice.coupon.dto.request;

import com.bookservice.coupon.entity.Coupon;
import lombok.Getter;

@Getter
public class CouponRegisterRequest {
	private String name;
	private Integer amount;
	private Long discountValue;

	public Coupon toCoupon() {
		return Coupon.builder()
					   .name(name)
					   .amount(amount)
					   .discountValue(discountValue)
					   .build();
	}
}
