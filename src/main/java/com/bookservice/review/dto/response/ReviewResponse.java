package com.bookservice.review.dto.response;

import com.bookservice.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {
	private Long reviewId;
	private String member;
	private Integer rating;
	private String content;
	private LocalDateTime createdAt;

	@Builder
	public ReviewResponse(Long reviewId, String member, Integer rating, String content, LocalDateTime createdAt) {
		this.reviewId = reviewId;
		this.member = member;
		this.rating = rating;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static ReviewResponse toList(Review review) {
		return ReviewResponse.builder()
					   .reviewId(review.getId())
					   .member(review.getMember().getNickName())
					   .rating(review.getRating())
					   .content(review.getContent())
					   .createdAt(review.getCreatedAt())
					   .build();
	}
}
