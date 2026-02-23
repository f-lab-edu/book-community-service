package com.bookservice.book.controller;

import com.bookservice.book.dto.request.BookRegisterRequest;
import com.bookservice.book.dto.request.BookSearchRequest;
import com.bookservice.book.dto.request.BookUpdateRequest;
import com.bookservice.book.dto.response.BookResponse;
import com.bookservice.book.service.BookService;
import com.bookservice.common.response.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

	private final BookService bookService;

	@PostMapping("/book")
	public ResponseEntity<SuccessMessage<Void>> registerBook(@Valid @RequestBody BookRegisterRequest request){
		bookService.registerBook(request);
		return new ResponseEntity<>(new SuccessMessage<>("책등록성공", null), HttpStatus.CREATED);
	}

	@PutMapping("/{bookId}")
	public ResponseEntity<SuccessMessage<Void>> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookUpdateRequest request){
		bookService.updateBook(bookId, request);
		return new ResponseEntity<>(new SuccessMessage<>("책수정성공", null), HttpStatus.OK);
	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<SuccessMessage<Void>> deleteBook(@PathVariable Long bookId){
		bookService.deleteBook(bookId);
		return new ResponseEntity<>(new SuccessMessage<>("책삭제성공", null), HttpStatus.OK);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<SuccessMessage<BookResponse>> getBookInfo(@PathVariable Long bookId){
		BookResponse bookInfo = bookService.getBookInfo(bookId);
		return new ResponseEntity<>(new SuccessMessage<>("책조회성공", bookInfo), HttpStatus.OK);
	}

	@GetMapping("/allBooks")
	public ResponseEntity<SuccessMessage<List<BookResponse>>> getBookListResponse(
			@RequestBody BookSearchRequest searchRequest, Pageable pageable){
		List<BookResponse> bookList = bookService.getBookListResponse(searchRequest, pageable);
		return new ResponseEntity<>(new SuccessMessage<>("모든책조회성공", bookList), HttpStatus.OK);
	}

	@GetMapping("/bestSellers")
	public ResponseEntity<SuccessMessage<List<BookResponse>>> getBestSellersResponse(Pageable pageable){
		List<BookResponse> bookList = bookService.getBestSellersResponse(pageable);
		return new ResponseEntity<>(new SuccessMessage<>("실시간베스트셀러조회성공", bookList), HttpStatus.OK);
	}
}
