package com.bookservice.coupon.controller;

import com.bookservice.common.response.SuccessMessage;
import com.bookservice.coupon.dto.request.CouponRegisterRequest;
import com.bookservice.coupon.dto.request.CouponUpdateRequest;
import com.bookservice.coupon.dto.response.CouponListResponse;
import com.bookservice.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CouponController {
	private final CouponService couponService;

	@PostMapping("/coupon")
	public ResponseEntity<SuccessMessage<Void>> registerCoupon(@Valid @RequestBody CouponRegisterRequest request){
		couponService.registerCoupon(request);
		return new ResponseEntity<>(new SuccessMessage<>("쿠폰등록성공", null), HttpStatus.CREATED);
	}

	@PutMapping("/coupon/{couponId}")
	public ResponseEntity<SuccessMessage<Void>> updateCoupon(@PathVariable Long couponId, @Valid @RequestBody CouponUpdateRequest request){
		couponService.updateCoupon(couponId, request);
		return new ResponseEntity<>(new SuccessMessage<>("쿠폰수정성공", null), HttpStatus.OK);
	}

	@DeleteMapping("/coupon/{couponId}")
	public ResponseEntity<SuccessMessage<Void>> deleteCoupon(@PathVariable Long couponId){
		couponService.deleteCoupon(couponId);
		return new ResponseEntity<>(new SuccessMessage<>("쿠폰삭제성공", null), HttpStatus.OK);
	}

	@GetMapping("/coupons")
	public ResponseEntity<SuccessMessage<CouponListResponse>> getAllCoupons(){
		CouponListResponse response = couponService.getAllCoupons();
		return new ResponseEntity<>(new SuccessMessage<>("모든쿠폰조회성공", response), HttpStatus.OK);
	}
}
