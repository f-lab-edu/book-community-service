package com.bookservice.author.dto.response;

import com.bookservice.author.entity.Author;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthorInfo {
	private final Long authorId;
	private final String name;

	@Builder
	public AuthorInfo(Long authorId, String name) {
		this.authorId = authorId;
		this.name = name;
	}

	public static AuthorInfo toAuthor(Author author) {
		return new AuthorInfo(
				author.getId(),
				author.getName()
		);
	}
}
