package com.bookservice.book.dto.response;

import com.bookservice.book.entity.Book;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BookResponse {
	private Long bookId;
	private String title;
	private String thumbnail;
	private String description;
	private String releaseDate;
	private Integer viewCount;
	private Integer interestedCount;
	private Integer averageRating;
	private Integer reviewCount;
	private Boolean isFree;
	private Integer price;
	private String author;
	private List<String> bookHashTags;

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

	@Builder
	@QueryProjection
	public BookResponse(Long bookId, String title, String thumbnail, String description, String releaseDate, Integer viewCount, Integer interestedCount, Integer averageRating, Integer reviewCount, Boolean isFree, Integer price, String author, List<String> bookHashTags) {
		this.bookId = bookId;
		this.title = title;
		this.thumbnail = thumbnail;
		this.description = description;
		this.releaseDate = releaseDate;
		this.viewCount = viewCount;
		this.interestedCount = interestedCount;
		this.averageRating = averageRating;
		this.reviewCount = reviewCount;
		this.isFree = isFree;
		this.price = price;
		this.author = author;
		this.bookHashTags = bookHashTags;
	}
}
