package com.bookservice.coupon.service;

import com.bookservice.common.exception.BookException;
import com.bookservice.coupon.dto.request.CouponRegisterRequest;
import com.bookservice.coupon.dto.request.CouponUpdateRequest;
import com.bookservice.coupon.dto.response.CouponListResponse;
import com.bookservice.coupon.entity.Coupon;
import com.bookservice.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_COUPON;

@Service
@RequiredArgsConstructor
public class CouponService {
	private final CouponRepository couponRepository;

	@Transactional
	public void registerCoupon(CouponRegisterRequest request) {
		Coupon coupon = request.toCoupon();
		couponRepository.save(coupon);
	}

	@Transactional
	public void updateCoupon(Long couponId, CouponUpdateRequest request) {
		Coupon coupon = couponRepository.findById(couponId).orElseThrow(
				() -> new BookException(NOT_FOUND_COUPON));
		coupon.update(request.getName(), request.getAmount(), request.getDiscountValue());
	}

	@Transactional
	public void deleteCoupon(Long couponId) {
		couponRepository.deleteById(couponId);
	}

	@Transactional(readOnly = true)
	public CouponListResponse getAllCoupons() {
		List<Coupon> coupons = couponRepository.findAll();
		return CouponListResponse.toResponse(coupons);
	}
}
