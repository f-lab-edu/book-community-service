package com.bookservice.book.fixture;

import com.bookservice.book.entity.Book;

public class BookFixture {
	public static final String TITLE = "테스트타이틀";
	public static final String THUMBNAIL = "테스트썸네일";
	public static final String DESCRIPTION = "테스트설명";
	public static final String RELEASE_DATE = "테스트발행일";
	public static final String AUTHOR = "테스트작가";
	public static final String HASH_01 = "코미디";
	public static final String HASH_02 = "로맨스";

	public static Book toExistsBook(String title, String thumbnail, String description){
		return Book.builder()
					   .title(title)
					   .thumbnail(thumbnail)
					   .description(description)
					   .build();
	}
}
