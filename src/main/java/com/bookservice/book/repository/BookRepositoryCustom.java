package com.bookservice.book.repository;

import com.bookservice.book.entity.Book;

import java.util.Optional;

public interface BookRepositoryCustom {
	Optional<Book> findByIdWithHashTags(Long id);
	void updateViews(Long bookId);
}
