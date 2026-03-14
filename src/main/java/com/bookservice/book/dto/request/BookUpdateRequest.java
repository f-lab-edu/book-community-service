package com.bookservice.book.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookUpdateRequest{
	private String title;
	private String thumbnail;
	private String description;
	private boolean isFree;
	private Integer price;
	private List<String> hashTags;
}
