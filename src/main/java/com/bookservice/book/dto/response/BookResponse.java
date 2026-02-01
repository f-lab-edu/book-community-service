package com.bookservice.book.dto.response;

import com.bookservice.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BookResponse {
	private final Long bookId;
	private final String title;
	private final String thumbnail;
	private final String description;
	private final String releaseDate;
	private final Integer viewCount;
	private final Integer interestedCount;
	private final Integer averageRating;
	private final Integer reviewCount;
	private final Boolean isFree;
	private final Integer price;
	private final String author;
	private final List<String> bookHashTags;

	public static BookResponse toBook(Book book) {
		return new BookResponse(
				book.getId(),
				book.getTitle(),
				book.getThumbnail(),
				book.getDescription(),
				book.getReleaseDate(),
				book.getViewCount(),
				book.getInterestedCount(),
				book.getAverageRating(),
				book.getReviewCount(),
				book.getIsFree(),
				book.getPrice(),
				book.getAuthor().getName(),
				book.getBookHashTags().stream()
						.map(bookHashTag -> bookHashTag.getHashTag().getName())
						.collect(Collectors.toList())
		);
	}
}
