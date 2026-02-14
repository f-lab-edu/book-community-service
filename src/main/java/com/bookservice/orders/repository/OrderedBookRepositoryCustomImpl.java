package com.bookservice.orders.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bookservice.orders.domain.QOrderedBook.orderedBook;
import static com.bookservice.orders.domain.QOrders.orders;

@Repository
@RequiredArgsConstructor
public class OrderedBookRepositoryCustomImpl implements OrderedBookRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public boolean existsOrderedBook(Long bookId, Long memberId) {

		Integer result = queryFactory.selectOne()
							.from(orderedBook)
							.join(orderedBook.orders, orders)
							.where(orderedBook.book.id.eq(bookId),
									orders.member.id.eq(memberId))
							.fetchFirst();

		return result != null;
	}
}
