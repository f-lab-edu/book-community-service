package com.bookservice.orders.service;

import com.bookservice.book.entity.Book;
import com.bookservice.book.repository.BookRepository;
import com.bookservice.common.exception.BookException;
import com.bookservice.orders.repository.OrderedBookMapper;
import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.coupon.entity.Coupon;
import com.bookservice.coupon.entity.MemberCoupon;
import com.bookservice.coupon.repository.MemberCouponRepository;
import com.bookservice.member.entity.Member;
import com.bookservice.orders.domain.Orders;
import com.bookservice.orders.domain.Payment;
import com.bookservice.orders.dto.request.OrdersRegisterRequest;
import com.bookservice.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OrdersService {

	private final BookRepository bookRepository;
	private final OrdersRepository ordersRepository;
	private final MemberCouponRepository memberCouponRepository;
	private final OrderedBookMapper orderedBookMapper;

	//분산락을 이용한 동시성 처리 필요. -> 서버를 여러개 쓸거라서
	@Transactional
	public void purchaseBook(UserDetailsImpl userDetails, OrdersRegisterRequest request, Long bookId) {
		Long couponId = request.getCouponId();
		Member member = userDetails.getMember();
		Long discountAmount = 0L;

		if(orderedBookMapper.existsOrderedBook(bookId, member.getId())){
			throw new BookException(ALREADY_OWNED_BOOK);
		}

		Book book = bookRepository.findById(bookId).orElseThrow(
				() -> new BookException(NOT_FOUND_BOOK));

		discountAmount = findCanUseCouponAtMemberId(couponId, member, discountAmount);

		Orders orders = request.toOrders(member);
		orders.toOrderedBook(book);

		Payment payment = Payment.createPayment(
				request.getTotalPrice(),
				discountAmount,
				request.getPaymentType()
		);

		orders.toPayment(payment);

		ordersRepository.save(orders);
	}

	private Long findCanUseCouponAtMemberId(Long couponId, Member member, Long discountAmount) {
		if(couponId != null){
			MemberCoupon memberCoupon = memberCouponRepository.findByMemberIdAndCouponId(member.getId(), couponId);
			memberCoupon.use();
			Coupon coupon = memberCoupon.getCoupon();

			discountAmount = coupon.getDiscountValue();
		}
		return discountAmount;
	}
}
