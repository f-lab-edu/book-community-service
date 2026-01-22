package com.bookservice.review.service;

import com.bookservice.book.entity.Book;
import com.bookservice.book.repository.BookRepository;
import com.bookservice.common.exception.BookException;
import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.member.entity.Member;
import com.bookservice.review.dto.response.ReviewListResponse;
import com.bookservice.review.dto.request.ReviewRegisterRequest;
import com.bookservice.review.dto.request.ReviewUpdateRequest;
import com.bookservice.review.entity.Review;
import com.bookservice.review.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_BOOK;
import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_REVIEW;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final BookRepository bookRepository;

	@Transactional
	public void registerReview(Member currentMember,
							   Long bookId,
							   @Valid ReviewRegisterRequest request) {

		Book book = bookRepository.findById(bookId).orElseThrow(
				() -> new BookException(NOT_FOUND_BOOK));

		Review review = request.toReview(currentMember, book);
		reviewRepository.save(review);
	}

	@Transactional
	public void updateReview(UserDetailsImpl userDetails,
							 Long reviewId,
							 @Valid ReviewUpdateRequest request) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(
				() -> new BookException(NOT_FOUND_REVIEW));

		review.validateOwner(userDetails.getMember());

		review.update(request.getRating(), request.getContent());
	}

	@Transactional
	public void deleteReview(UserDetailsImpl userDetails, Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(
				() -> new BookException(NOT_FOUND_REVIEW));
		review.validateOwner(userDetails.getMember());

		reviewRepository.deleteById(reviewId);
	}

	@Transactional(readOnly = true)
	public ReviewListResponse getAllReviewsByBookId(Long bookId) {
		List<Review> reviews = reviewRepository.findByBookIdToAllReviews(bookId);
		return ReviewListResponse.toResponse(reviews);
	}
}
