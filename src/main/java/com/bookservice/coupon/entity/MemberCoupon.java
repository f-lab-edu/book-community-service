package com.bookservice.coupon.entity;

import com.bookservice.common.time.TimeStamped;
import com.bookservice.coupon.entity.enums.CouponStatus;
import com.bookservice.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

import static com.bookservice.coupon.entity.enums.CouponStatus.AVAILABLE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends TimeStamped {
	@Id
	@Column(name = "member_coupon_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="coupon_id")
	private Coupon coupon;

	private CouponStatus status;
	private LocalDateTime issuedAt;	//발급일
	private Date usedAt;	//사용일

	@Builder
	public MemberCoupon(Member member, Coupon coupon, CouponStatus status) {
		this.member = member;
		this.coupon = coupon;
		this.status = AVAILABLE;
		this.issuedAt = LocalDateTime.now();
	}

	public static MemberCoupon createMemberCoupon(Member member, Coupon coupon) {
		return MemberCoupon.builder()
					   .member(member)
					   .coupon(coupon)
					   .build();
	}
}
