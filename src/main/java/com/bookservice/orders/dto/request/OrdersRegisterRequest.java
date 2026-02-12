package com.bookservice.orders.dto.request;

import com.bookservice.member.entity.Member;
import com.bookservice.orders.domain.Orders;
import com.bookservice.orders.domain.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrdersRegisterRequest {
	private Long couponId;
	private LocalDateTime ordersDate;
	private Long totalPrice;
	private PaymentType paymentType;

	public Orders toOrders(Member member) {
		return Orders.builder()
					   .ordersDate(LocalDateTime.now())
					   .totalPrice(totalPrice)
					   .member(member)
					   .build();
	}
}
