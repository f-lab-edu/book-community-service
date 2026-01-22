package com.bookservice.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewUpdateRequest {
	private Integer rating;
	private String content;
}
