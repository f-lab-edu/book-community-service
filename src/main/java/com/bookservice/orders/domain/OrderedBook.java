package com.bookservice.orders.domain;

import com.bookservice.book.entity.Book;
import com.bookservice.common.time.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class OrderedBook extends TimeStamped {
	@Id
	@Column(name = "ordered_book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDateTime orderedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="orders_id")
	private Orders orders;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="book_id")
	private Book book;

	@Builder
	public OrderedBook(Orders orders, Book book) {
		this.orderedAt = LocalDateTime.now();
		this.orders = orders;
		this.book = book;
	}
}
