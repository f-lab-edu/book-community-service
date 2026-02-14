package com.bookservice.orders.repository;

public interface OrderedBookRepositoryCustom {
	boolean existsOrderedBook(Long bookId, Long memberId);
}
