package com.bookservice.author.dto.request;

import com.bookservice.author.entity.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorRegisterRequest {
	private String name;

	public Author toAuthor() {
		return Author.builder()
					.name(name)
					.build();
	}
}
