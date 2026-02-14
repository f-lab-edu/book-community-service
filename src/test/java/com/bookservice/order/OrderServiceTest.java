package com.bookservice.order;

import com.bookservice.book.entity.Book;
import com.bookservice.book.repository.BookRepository;
import com.bookservice.common.exception.BookException;
import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.coupon.entity.Coupon;
import com.bookservice.coupon.entity.MemberCoupon;
import com.bookservice.coupon.repository.MemberCouponRepository;
import com.bookservice.member.entity.Member;
import com.bookservice.member.fixture.MemberFixture;
import com.bookservice.orders.domain.Orders;
import com.bookservice.orders.dto.request.OrdersRegisterRequest;
import com.bookservice.orders.repository.OrderedBookRepository;
import com.bookservice.orders.repository.OrdersRepository;
import com.bookservice.orders.service.OrdersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.bookservice.book.fixture.BookFixture.*;
import static com.bookservice.common.exception.ErrorCode.ALREADY_OWNED_BOOK;
import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_BOOK;
import static com.bookservice.member.fixture.MemberFixture.*;
import static com.bookservice.orders.domain.enums.PaymentType.CASH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	@InjectMocks
	private OrdersService ordersService;
	@Mock
	private OrderedBookRepository orderedBookRepository;
	@Mock
	private BookRepository bookRepository;
	@Mock
	private MemberCouponRepository memberCouponRepository;
	@Mock
	private OrdersRepository ordersRepository;
	
	@Test
	@DisplayName("주문_완료")
	public void 주문_완료_쿠폰_사용(){
		//given
		Long bookId = 1L;
		Long couponId = 1L;

		Member member = MemberFixture.toMember(USER_EMAIL, PASSWORD, NICKNAME);
		UserDetailsImpl userDetails = new UserDetailsImpl(member, member.getEmail());

		Book book = toExistsBook(TITLE, THUMBNAIL, DESCRIPTION);

		Coupon coupon = Coupon.builder()
								.name("테스트쿠폰이름")
								.amount(999)
								.discountValue(3000L)
								.build();
		MemberCoupon memberCoupon = MemberCoupon.builder()
									 .member(member)
									 .coupon(coupon)
									 .build();

		OrdersRegisterRequest request = new OrdersRegisterRequest(1L, LocalDateTime.now(), 2000L, CASH);

		given(orderedBookRepository.existsOrderedBook(bookId, member.getId())).willReturn(false);
		given(bookRepository.findById(bookId)).willReturn(Optional.of(book));
		given(memberCouponRepository.findByMemberIdAndCouponId(member.getId(), couponId)).willReturn(memberCoupon);

		//when
		ordersService.purchaseBook(userDetails, request, bookId);

		//then
		verify(memberCouponRepository, times(1)).findByMemberIdAndCouponId(member.getId(), couponId);
		verify(ordersRepository, times(1)).save(any(Orders.class));
	}

	@Test
	@DisplayName("주문_실패_이미_구매한_책")
	public void 주문_실패_이미_구매한_책(){
	    //given
		Long bookId = 1L;
		Member member = MemberFixture.toMember(USER_EMAIL, PASSWORD, NICKNAME);
		UserDetailsImpl userDetails = new UserDetailsImpl(member, member.getEmail());
		OrdersRegisterRequest request = new OrdersRegisterRequest(1L, LocalDateTime.now(), 2000L, CASH);

		given(orderedBookRepository.existsOrderedBook(bookId, member.getId())).willReturn(true);

		//when&then
		assertThatThrownBy(() -> ordersService.purchaseBook(userDetails, request, bookId))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", ALREADY_OWNED_BOOK);
	}
	
	@Test
	@DisplayName("주문_실패_등록_되지_않은_책")
	public void 주문_실패_등록_되지_않은_책(){
	    //given
		Long bookId = 1L;
		Member member = MemberFixture.toMember(USER_EMAIL, PASSWORD, NICKNAME);
		UserDetailsImpl userDetails = new UserDetailsImpl(member, member.getEmail());
		OrdersRegisterRequest request = new OrdersRegisterRequest(1L, LocalDateTime.now(), 2000L, CASH);

		given(orderedBookRepository.existsOrderedBook(bookId, member.getId())).willReturn(false);
		given(bookRepository.findById(bookId)).willReturn(Optional.empty());

		//when & then
		assertThatThrownBy(() -> ordersService.purchaseBook(userDetails, request, bookId))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", NOT_FOUND_BOOK);
	}
}
