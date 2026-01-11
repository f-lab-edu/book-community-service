package com.bookservice.book.entity;

import com.bookservice.common.time.TimeStamped;
import com.bookservice.hashtag.entity.HashTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookHashTag extends TimeStamped {
	@Id
	@Column(name = "book_hash_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="book_id")
	private Book book;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="hash_tag_id")
	private HashTag hashTag;

	@Builder
	public BookHashTag(Long id, Book book, HashTag hashTag) {
		this.id = id;
		this.book = book;
		this.hashTag = hashTag;
	}
}
