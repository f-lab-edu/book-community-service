package com.bookservice.coupon.controller;

import com.bookservice.common.response.SuccessMessage;
import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.coupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me/coupons")
public class MemberCouponController {
	private final MemberCouponService memberCouponService;

	@PostMapping("/{couponId}")
	public ResponseEntity<SuccessMessage<Void>> registerMemberCoupon(@AuthenticationPrincipal UserDetailsImpl userDetails,
																	 @PathVariable Long couponId){
		memberCouponService.registerMemberCoupon(userDetails, couponId);
		return new ResponseEntity<>(new SuccessMessage<>("쿠폰발급성공", null), HttpStatus.CREATED);
	}
}
