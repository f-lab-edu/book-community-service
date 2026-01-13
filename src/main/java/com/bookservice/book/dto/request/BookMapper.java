package com.bookservice.book.dto.request;

import com.bookservice.book.entity.Book;
import com.bookservice.book.entity.BookHashTag;
import com.bookservice.hashtag.entity.HashTag;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
	public BookHashTag toBookHashTag(Book book, HashTag hashTag) {
		return BookHashTag.builder()
					   .book(book)
					   .hashTag(hashTag)
					   .build();
	}
}
