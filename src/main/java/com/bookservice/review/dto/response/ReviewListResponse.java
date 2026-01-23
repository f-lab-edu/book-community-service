package com.bookservice.review.dto.response;

import com.bookservice.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewListResponse {
	List<ReviewResponse> reviews;

	public static ReviewListResponse toResponse(List<Review> reviews) {
		List<ReviewResponse> reviewList = reviews.stream()
											.map(ReviewResponse::toList)
											.toList();

		return new ReviewListResponse(reviewList);
	}
}
