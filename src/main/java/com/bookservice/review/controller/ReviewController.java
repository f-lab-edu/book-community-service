package com.bookservice.review.controller;

import com.bookservice.common.response.SuccessMessage;
import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.review.dto.request.ReviewRegisterRequest;
import com.bookservice.review.dto.request.ReviewUpdateRequest;
import com.bookservice.review.dto.response.ReviewListResponse;
import com.bookservice.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/book/{bookId}/review")
	public ResponseEntity<SuccessMessage<Void>> registerReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
															   @PathVariable Long bookId,
															   @Valid @RequestBody ReviewRegisterRequest request){
		reviewService.registerReview(userDetails.getMember(), bookId, request);
		return new ResponseEntity<>(new SuccessMessage<>("리뷰등록성공", null), HttpStatus.CREATED);
	}

	@PutMapping("/review/{reviewId}")
	public ResponseEntity<SuccessMessage<Void>> updateReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
															 @PathVariable Long reviewId,
															 @Valid @RequestBody ReviewUpdateRequest request){
		reviewService.updateReview(userDetails, reviewId, request);
		return new ResponseEntity<>(new SuccessMessage<>("리뷰수정성공", null), HttpStatus.OK);
	}

	@DeleteMapping("/review/{reviewId}")
	public ResponseEntity<SuccessMessage<Void>> deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
															 @PathVariable Long reviewId){
		reviewService.deleteReview(userDetails, reviewId);
		return new ResponseEntity<>(new SuccessMessage<>("리뷰삭제성공", null), HttpStatus.OK);
	}

	@GetMapping("/book/{bookId}/reviews")
	public ResponseEntity<SuccessMessage<ReviewListResponse>> getAllReviewsByBookId(@PathVariable Long bookId){
		ReviewListResponse response = reviewService.getAllReviewsByBookId(bookId);
		return new ResponseEntity<>(new SuccessMessage<>("등록된리뷰조회성공", response), HttpStatus.OK);
	}
}
