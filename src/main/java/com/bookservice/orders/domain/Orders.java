package com.bookservice.orders.domain;

import com.bookservice.book.entity.Book;
import com.bookservice.common.time.TimeStamped;
import com.bookservice.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Orders extends TimeStamped {
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDateTime ordersDate;

	@NotNull
	@ColumnDefault("0")
	private Long totalPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<OrderedBook> orderedBook = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="payment_id")
	private Payment payment;

	@Builder
	public Orders(LocalDateTime ordersDate, Long totalPrice, Member member) {
		this.ordersDate = ordersDate;
		this.totalPrice = totalPrice;
		this.member = member;
	}

	public void toOrderedBook(Book book) {
		OrderedBook registerBook = new OrderedBook(this, book);
		this.orderedBook.add(registerBook);
	}

	public void toPayment(Payment payment) {
		this.payment = payment;
	}
}
