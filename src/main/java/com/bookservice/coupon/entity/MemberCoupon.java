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

import static com.bookservice.coupon.entity.enums.CouponStatus.AVAILABLE;
import static com.bookservice.coupon.entity.enums.CouponStatus.USED;

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

	@Enumerated(EnumType.STRING)
	private CouponStatus status;

	private LocalDateTime issuedAt;	//발급일
	private LocalDateTime usedAt;	//사용일

	@Builder
	public MemberCoupon(Member member, Coupon coupon) {
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

	public void use() {
		this.status = USED;
		this.usedAt = LocalDateTime.now();
	}
}
