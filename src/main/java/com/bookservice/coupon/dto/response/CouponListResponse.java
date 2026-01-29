package com.bookservice.coupon.dto.response;

import com.bookservice.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CouponListResponse {
	List<CouponResponse> coupons;

	public static CouponListResponse toResponse(List<Coupon> coupons) {
		List<CouponResponse> list = coupons.stream()
											.map(CouponResponse::toList)
											.toList();
		return new CouponListResponse(list);
	}
}
