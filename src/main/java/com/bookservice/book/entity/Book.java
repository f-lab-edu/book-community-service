package com.bookservice.book.entity;

import com.bookservice.author.entity.Author;
import com.bookservice.common.time.TimeStamped;
import com.bookservice.hashtag.entity.HashTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Table(uniqueConstraints = {
		@UniqueConstraint(
				name = "uk_book_title",
				columnNames = {"title"}
		)
})
public class Book extends TimeStamped {
	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String title;

	@NotNull
	private String thumbnail;

	@NotNull
	private String description;

	@NotNull
	private String releaseDate;

	@ColumnDefault("0")
	private Integer viewCount;

	@ColumnDefault("0")
	private Integer interestedCount;

	@ColumnDefault("1")
	private Integer averageRating;

	@ColumnDefault("0")
	private Integer reviewCount;

	@ColumnDefault("true")
	private Boolean isFree;

	@ColumnDefault("0")
	private Integer price;

	@ManyToOne
	@JoinColumn(name="author_id")
	private Author author;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<BookHashTag> bookHashTags = new ArrayList<>();

	@Builder
	public Book(String title, String thumbnail, String description, String releaseDate, Author author) {
		this.title = title;
		this.thumbnail = thumbnail;
		this.description = description;
		this.releaseDate = releaseDate;
		this.author = author;
	}

	private void addHashTag(HashTag hashTag){
		BookHashTag bookHashTag = new BookHashTag(this, hashTag);
		this.bookHashTags.add(bookHashTag);
	}

	public void addHashTags(List<HashTag> newHashTags){
		newHashTags.forEach(this::addHashTag);
	}

	public void update(String title, String thumbnail, String description, List<HashTag> newHashTags) {
		this.title = title;
		this.thumbnail = thumbnail;
		this.description = description;

		this.bookHashTags.clear();

		this.addHashTags(newHashTags);
	}
}
