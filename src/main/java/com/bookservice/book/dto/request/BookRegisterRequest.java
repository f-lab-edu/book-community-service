package com.bookservice.book.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class BookRegisterRequest{
	private String title;
	private String thumbnail;
	private String description;
	private LocalDate releaseDate;
	private boolean isFree;
	private Integer price;
	private String author;
	private List<String> hashTags;
}
