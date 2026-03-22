package com.bookservice.book.dto.command;

import com.bookservice.author.entity.Author;

import java.time.LocalDate;

public record BookCreateCommand(
		String title,
		String thumbnail,
		String description,
		LocalDate releaseDate,
		Boolean isFree,
		Long amount,
		Author author
) {
}
