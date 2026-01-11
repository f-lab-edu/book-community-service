package com.bookservice.book.dto.request;

import com.bookservice.author.entity.Author;
import com.bookservice.book.entity.Book;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookRegisterRequest{
	private String title;
	private String thumbnail;
	private String description;
	private String releaseDate;
	private String author;
	private List<String> hashTags;

	@Builder
	public Book toBook(Author author) {
		return Book.builder()
					.title(title)
					.thumbnail(thumbnail)
					.description(description)
					.releaseDate(releaseDate)
					.author(author)
					.build();
	}
}
