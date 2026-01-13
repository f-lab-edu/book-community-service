package com.bookservice.book.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class BookUpdateRequest{
	private String title;
	private String thumbnail;
	private String description;
	private String releaseDate;
	private List<String> hashTags;
}
