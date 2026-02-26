package com.bookservice.coupon.repository;

import com.bookservice.coupon.dto.response.UsableCouponResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bookservice.coupon.entity.QCoupon.coupon;
import static com.bookservice.coupon.entity.QMemberCoupon.memberCoupon;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<UsableCouponResponse> getUsableCoupons(Long memberId) {
		return queryFactory
					   .select(Projections.fields(UsableCouponResponse.class,
							   memberCoupon.id.as("couponId"),
							   coupon.name.as("name"),
							   memberCoupon.status.as("status"),
							   memberCoupon.issuedAt.as("issuedAt"),
							   coupon.endDate.as("endDate"),
							   coupon.discountValue.as("discountValue"),
							   coupon.createdAt.as("createdAt")
					   ))
					   .join(memberCoupon)
					   .join(memberCoupon.coupon, coupon)
					   .where(
							   memberCoupon.member.id.eq(memberId)
					   )
					   .orderBy(memberCoupon.coupon.id.asc())
					   .fetch();
	}
}
