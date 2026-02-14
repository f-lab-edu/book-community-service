package com.bookservice.book.repository;

import com.bookservice.book.entity.Book;
import com.bookservice.book.entity.QBook;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.bookservice.book.entity.QBook.book;
import static com.bookservice.book.entity.QBookHashTag.bookHashTag;
import static com.bookservice.hashtag.entity.QHashTag.hashTag;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Book> findByIdWithHashTags(Long id) {
		Book result = queryFactory
							 .selectFrom(QBook.book)
							 .join(QBook.book.bookHashTags, bookHashTag).fetchJoin()
							 .join(bookHashTag.hashTag, hashTag).fetchJoin()
							 .where(QBook.book.id.eq(id))
							 .fetchOne();

		return Optional.ofNullable(result);
	}

	@Override
	public void updateViews(Long bookId) {
		queryFactory.update(book)
				.set(book.viewCount, book.viewCount.add(1))
				.where(book.id.eq(bookId))
				.execute();
	}
}
