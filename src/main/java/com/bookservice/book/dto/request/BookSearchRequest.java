package com.bookservice.book.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookSearchRequest {
	private final String searchTitle;
	private final Integer minPrice;
	private final Integer maxPrice;
}
