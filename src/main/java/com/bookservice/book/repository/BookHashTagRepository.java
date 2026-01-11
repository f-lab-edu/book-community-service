package com.bookservice.book.repository;

import com.bookservice.book.entity.BookHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookHashTagRepository extends JpaRepository<BookHashTag, Long> {
	void deleteByBookId(Long bookId);
}
