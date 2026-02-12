package com.bookservice.orders.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderedBookMapper {
	boolean existsOrderedBook(Long bookId, Long memberId);
}
