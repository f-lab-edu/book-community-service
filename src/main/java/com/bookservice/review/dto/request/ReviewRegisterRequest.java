package com.bookservice.review.dto.request;

import com.bookservice.book.entity.Book;
import com.bookservice.member.entity.Member;
import com.bookservice.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRegisterRequest {
	private Integer rating;
	private String content;

	@Builder
	public Review toReview(Member member, Book book){
		return Review.builder()
					   .rating(rating)
					   .content(content)
					   .member(member)
					   .book(book)
					   .build();
	}
}
