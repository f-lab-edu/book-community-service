package com.bookservice.coupon.dto.response;

import com.bookservice.coupon.entity.Coupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponResponse {
	private final Long couponId;
	private final String name;
	private final Integer amount;
	private final Long discountValue;

	@Builder
	public CouponResponse(Long couponId, String name, Integer amount, Long discountValue) {
		this.couponId = couponId;
		this.name = name;
		this.amount = amount;
		this.discountValue = discountValue;
	}

	public static CouponResponse toList(Coupon coupon) {
		return CouponResponse.builder()
					   .couponId(coupon.getId())
					   .name(coupon.getName())
					   .amount(coupon.getAmount())
					   .discountValue(coupon.getDiscountValue())
					   .build();
	}
}
