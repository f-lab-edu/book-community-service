package com.bookservice.book.repository;

import com.bookservice.book.dto.request.BookSearchRequest;
import com.bookservice.book.dto.response.BookResponse;
import com.bookservice.book.entity.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryCustom {
	Optional<Book> findByIdWithHashTags(Long id);
	void updateViews(Long bookId);
	List<BookResponse> findBookListResponse(BookSearchRequest searchRequest, Pageable pageable);
	List<BookResponse> getBestSellersResponse(Pageable pageable);
}
