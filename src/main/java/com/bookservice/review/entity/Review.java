package com.bookservice.review.entity;

import com.bookservice.book.entity.Book;
import com.bookservice.common.exception.BookException;
import com.bookservice.common.time.TimeStamped;
import com.bookservice.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import static com.bookservice.common.exception.ErrorCode.FORBIDDEN_ACCESS;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends TimeStamped {
	@Id
	@Column(name = "review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ColumnDefault("1")
	private Integer rating;

	@NotNull
	private String content;

	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;

	@Builder
	public Review(Integer rating, String content, Member member, Book book){
		this.rating = rating;
		this.content = content;
		this.member = member;
		this.book = book;
	}

	public void update(Integer rating, String content) {
		this.rating = rating;
		this.content = content;
	}

	public void validateOwner(Member member) {
		if(! this.member.getId().equals(member.getId())){
			throw new BookException(FORBIDDEN_ACCESS);
		}
	}
}
