package com.bookservice.book.service;

import com.bookservice.author.entity.Author;
import com.bookservice.author.repository.AuthorRepository;
import com.bookservice.book.dto.request.BookMapper;
import com.bookservice.book.dto.request.BookRegisterRequest;
import com.bookservice.book.dto.request.BookUpdateRequest;
import com.bookservice.book.dto.response.BookInfo;
import com.bookservice.book.entity.Book;
import com.bookservice.book.entity.BookHashTag;
import com.bookservice.book.repository.BookHashTagRepository;
import com.bookservice.book.repository.BookRepository;
import com.bookservice.common.exception.BookException;
import com.bookservice.hashtag.entity.HashTag;
import com.bookservice.hashtag.repository.HashTagRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bookservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final HashTagRepository hashTagRepository;
	private final BookHashTagRepository bookHashTagRepository;
	private final BookMapper bookMapper;

	@Transactional
	public void registerBook(@Valid BookRegisterRequest request) {
		Author author = authorRepository.findByName(request.getAuthor()).orElseThrow(
				() -> new BookException(NOT_FOUND_AUTHOR));
		Book book = request.toBook(author);
		bookRepository.save(book);

		addHashTags(request.getHashTags(), book);
	}

	@Transactional
	public void updateBook(Long bookId, @Valid BookUpdateRequest request) {
		Book book = bookRepository.findById(bookId).orElseThrow(
				() -> new BookException(NOT_FOUND_BOOK));
		book.update(request.getTitle(), request.getThumbnail(), request.getDescription());

		bookHashTagRepository.deleteByBookId(bookId);

		addHashTags(request.getHashTags(), book);
	}

	private void addHashTags(List<String> request, Book book) {
		List<String> requestNames = request.stream().distinct().toList();
		List<HashTag> tags = hashTagRepository.findAllByNameIn(requestNames);

		validateAllHashTagsExist(tags, requestNames);

		List<BookHashTag> bookHashTags = tags.stream()
												 .map(tag -> bookMapper.toBookHashTag(book, tag))
												 .collect(Collectors.toList());

		bookHashTagRepository.saveAll(bookHashTags);
	}

	private static void validateAllHashTagsExist(List<HashTag> tags, List<String> requestNames) {
		if (tags.size() != requestNames.size()) {
			throw new BookException(NOT_FOUND_HASH_TAG);
		}
	}

	@Transactional
	public void deleteBook(Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(
				() -> new BookException(NOT_FOUND_BOOK));
		bookRepository.delete(book);
	}

	@Transactional
	public Optional<BookInfo> getBookInfo(Long bookId) {
		bookRepository.updateViews(bookId);
		return bookRepository.findByIdWithHashTags(bookId)
					.map(BookInfo::toBook);
	}
}
