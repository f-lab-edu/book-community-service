package com.bookservice.book.service;

import com.bookservice.author.entity.Author;
import com.bookservice.author.repository.AuthorRepository;
import com.bookservice.book.dto.request.BookRegisterRequest;
import com.bookservice.book.dto.request.BookSearchRequest;
import com.bookservice.book.dto.request.BookUpdateRequest;
import com.bookservice.book.dto.response.BookResponse;
import com.bookservice.book.entity.Book;
import com.bookservice.book.repository.BookRepository;
import com.bookservice.common.exception.BookException;
import com.bookservice.hashtag.entity.HashTag;
import com.bookservice.hashtag.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bookservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final HashTagRepository hashTagRepository;

	@Transactional
	@CacheEvict(
			value = "weeklyBestSellers",
			key = "T(java.time.LocalDate).now().minusDays(7) + ' ~ ' + T(java.time.LocalDate).now() + ':page:' + #pageNo + ':size:' + #pageSize"
	)
	public void registerBook(BookRegisterRequest request) {
		Author author = authorRepository.findByName(request.getAuthor()).orElseThrow(
				() -> new BookException(NOT_FOUND_AUTHOR));

		List<HashTag> hashTags = findAllByNameIn(request.getHashTags());

		Book book = request.toBook(author);
		book.addHashTags(hashTags);

		bookRepository.save(book);
	}

	@Transactional
	@CacheEvict(
			value = "weeklyBestSellers",
			key = "T(java.time.LocalDate).now().minusDays(7) + ' ~ ' + T(java.time.LocalDate).now() + ':page:' + #pageNo + ':size:' + #pageSize"
	)
	public void updateBook(Long bookId, BookUpdateRequest request) {
		Book book = bookRepository.findById(bookId).orElseThrow(
				() -> new BookException(NOT_FOUND_BOOK));

		List<HashTag> tags = findAllByNameIn(request.getHashTags());

		book.update(
				request.getTitle(),
				request.getThumbnail(),
				request.getDescription(),
				tags
		);
	}

	private List<HashTag> findAllByNameIn(List<String> request) {
		List<String> newHashTags = request.stream().distinct().toList();
		List<HashTag> existsTags = hashTagRepository.findAllByNameIn(newHashTags);

		validateAllHashTagsExist(existsTags, newHashTags);

		return existsTags;
	}

	private static void validateAllHashTagsExist(List<HashTag> existsTags, List<String> newHashTags) {
		if (existsTags.size() != newHashTags.size()) {
			throw new BookException(NOT_FOUND_HASH_TAG);
		}
	}

	@Transactional
	@CacheEvict(
			value = "weeklyBestSellers",
			key = "T(java.time.LocalDate).now().minusDays(7) + ' ~ ' + T(java.time.LocalDate).now() + ':page:' + #pageNo + ':size:' + #pageSize"
	)
	public void deleteBook(Long bookId) {
		bookRepository.deleteById(bookId);
	}

	@Transactional(readOnly = true)
	public BookResponse getBookInfo(Long bookId) {
		bookRepository.updateViews(bookId);
		return bookRepository.findByIdWithHashTags(bookId)
					.map(BookResponse::toBook)
					.orElseThrow(() -> new BookException(NOT_FOUND_BOOK));
	}

	@Transactional(readOnly = true)
	public List<BookResponse> getBookListResponse(BookSearchRequest searchRequest, Pageable pageable) {
		return bookRepository.findBookListResponse(searchRequest, pageable);
	}

	@Transactional(readOnly = true)
	@Cacheable(
			value = "weeklyBestSellers",
			key = "T(java.time.LocalDate).now().minusDays(7) + ' ~ ' + T(java.time.LocalDate).now()",
			cacheManager = "cacheManager"
	)
	public List<BookResponse> getBestSellersResponse(Pageable pageable) {
		return bookRepository.getBestSellersResponse(pageable);
	}
}
