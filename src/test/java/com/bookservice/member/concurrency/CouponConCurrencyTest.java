package com.bookservice.member.concurrency;

import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.coupon.entity.Coupon;
import com.bookservice.coupon.repository.CouponRepository;
import com.bookservice.coupon.repository.MemberCouponRepository;
import com.bookservice.coupon.service.MemberCouponService;
import com.bookservice.member.entity.Member;
import com.bookservice.member.repository.MemberRepository;
import com.bookservice.orders.repository.OrdersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CouponConCurrencyTest {
	@Autowired
	private MemberCouponService memberCouponService;
	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberCouponRepository memberCouponRepository;
	@Autowired
	private OrdersRepository ordersRepository;

	private Long couponId;

	@BeforeEach
	void setUp(){
		//테스트용 쿠폰 100장 생성
		String uniqueName = "Coupon-" + UUID.randomUUID().toString();
		Coupon coupon = new Coupon(uniqueName, 100, 3000L);
		Coupon saveCoupon = couponRepository.save(coupon);

		this.couponId = saveCoupon.getId();
	}

	@AfterEach
	void tearDown() {
		ordersRepository.deleteAll();
		memberCouponRepository.deleteAll();

		couponRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("동시에_100장_쿠폰_발급")
	public void 동시에_100장_쿠폰_발급() throws InterruptedException {
	    //given
		int thread = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(thread);

		//when
		for (int i = 0; i < thread; i++) {
			long memberId = i+1;

			executorService.submit(() -> {
				try {
					Member member = Member.builder()
											.email("member" + memberId + "@gmail.com")
											.password("password")
											.nickName("nickname" + memberId)
											.build();
					memberRepository.save(member);

					UserDetailsImpl userDetails = new UserDetailsImpl(member, "email");

					memberCouponService.registerMemberCoupon(userDetails, couponId);
				}
				finally{
					latch.countDown();
				}
			});
		}
		latch.await();
	    //then
		Coupon updatedCoupon = couponRepository.findById(couponId).orElseThrow();

		assertThat(updatedCoupon.getAmount()).isEqualTo(0);
	}
}
