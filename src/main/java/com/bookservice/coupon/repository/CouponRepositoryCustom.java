package com.bookservice.coupon.repository;

import com.bookservice.coupon.dto.response.UsableCouponResponse;

import java.util.List;

public interface CouponRepositoryCustom {
	List<UsableCouponResponse> getUsableCoupons(Long memberId);
}
