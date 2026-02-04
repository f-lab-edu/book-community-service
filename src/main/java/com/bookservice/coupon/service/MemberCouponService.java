package com.bookservice.coupon.service;

import com.bookservice.common.aop.DistributedLock;
import com.bookservice.common.exception.BookException;
import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.coupon.entity.Coupon;
import com.bookservice.coupon.entity.MemberCoupon;
import com.bookservice.coupon.repository.CouponRepository;
import com.bookservice.coupon.repository.MemberCouponRepository;
import com.bookservice.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bookservice.common.exception.ErrorCode.ALREADY_EXIST_COUPON;
import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_COUPON;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
	private final MemberCouponRepository memberCouponRepository;
	private final CouponRepository couponRepository;

	@DistributedLock(key = "#couponId")
	public void registerMemberCoupon(UserDetailsImpl userDetails, Long couponId) {
		Coupon coupon = couponRepository.findById(couponId).orElseThrow(
				() -> new BookException(NOT_FOUND_COUPON));

		isAlreadyHasCoupon(couponId, userDetails.getMember());

		coupon.canIssuedCouponCheck();

		MemberCoupon memberCoupon = MemberCoupon.createMemberCoupon(userDetails.getMember(), coupon);
		memberCouponRepository.save(memberCoupon);
	}

	private void isAlreadyHasCoupon(Long couponId, Member member) {
		if(memberCouponRepository.existsByMemberIdAndCouponId(member.getId(), couponId)){
			throw new BookException(ALREADY_EXIST_COUPON);
		}
	}
}
