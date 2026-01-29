package com.bookservice.coupon.repository;

import com.bookservice.coupon.entity.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon,Long> {
	boolean existsByMemberIdAndCouponId(Long memberId, Long couponId);
}
