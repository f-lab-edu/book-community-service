package com.bookservice.book.repository;

import com.bookservice.book.dto.request.BookSearchRequest;
import com.bookservice.book.dto.response.BookResponse;
import com.bookservice.book.dto.response.QBookResponse;
import com.bookservice.book.entity.Book;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bookservice.author.entity.QAuthor.author;
import static com.bookservice.book.entity.QBook.book;
import static com.bookservice.book.entity.QBookHashTag.bookHashTag;
import static com.bookservice.hashtag.entity.QHashTag.hashTag;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Book> findByIdWithHashTags(Long id) {
		Book result = queryFactory
							  .selectFrom(book)
							  .join(book.bookHashTags, bookHashTag).fetchJoin()
							  .join(bookHashTag.hashTag, hashTag).fetchJoin()
							  .where(book.id.eq(id))
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

	@Override
	public List<BookResponse> findBookListResponse(BookSearchRequest searchRequest, Pageable pageable) {
		List<Long> bookIds = queryFactory
									 .select(book.id)
									 .from(book)
									 .orderBy(book.id.desc())
									 .offset(pageable.getOffset())
									 .limit(pageable.getPageSize())
									 .where(eqTitle(searchRequest.getSearchTitle()))
									 .where(betweenPrice(searchRequest.getMinPrice(), searchRequest.getMaxPrice()))
									 .fetch();

		return queryFactory
					   .from(book)
					   .join(book.author, author)
					   .leftJoin(book.bookHashTags, bookHashTag)
					   .leftJoin(bookHashTag.hashTag, hashTag)
					   .where(book.id.in(bookIds))
					   .orderBy(book.id.desc())
					   .transform(
							   groupBy(book.id).list(
									   new QBookResponse(
											   book.id,
											   book.title,
											   book.thumbnail,
											   book.description,
											   book.releaseDate,
											   book.viewCount,
											   book.interestedCount,
											   book.rating.averageRating,
											   book.reviewCount,
											   book.price.isFree,
											   book.price.amount,
											   book.author.name,
											   list(hashTag.name)
									   )
							   )
					   );
	}

	@Override
	public List<BookResponse> getBestSellersResponse(Pageable pageable) {
		LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);

		List<Long> bookIds = queryFactory
									 .select(book.id)
									 .from(book)
									 .where(
											 book.releaseDate.goe(LocalDate.from(oneWeekAgo))
									 )
									 .orderBy(
											 book.viewCount.desc(),
											 book.interestedCount.desc(),
											 book.id.desc()
									 )
									 .offset(pageable.getOffset())
									 .limit(pageable.getPageSize())
									 .fetch();

		return queryFactory
					   .from(book)
					   .join(book.author, author)
					   .leftJoin(book.bookHashTags, bookHashTag)
					   .leftJoin(bookHashTag.hashTag, hashTag)
					   .where(book.id.in(bookIds))
					   .orderBy(
							   // 조회수 > 관심등록수 > 최신등록 순
							   book.viewCount.desc(),
							   book.interestedCount.desc(),
							   book.id.desc()
					   )
					   .transform(
							   groupBy(book.id).list(
									   new QBookResponse(
											   book.id,
											   book.title,
											   book.thumbnail,
											   book.description,
											   book.releaseDate,
											   book.viewCount,
											   book.interestedCount,
											   book.rating.averageRating,
											   book.reviewCount,
											   book.price.isFree,
											   book.price.amount,
											   book.author.name,
											   list(hashTag.name)
									   )
							   )
					   );
	}

	private BooleanExpression eqTitle(String searchTitle) {
		return searchTitle != null ? book.title.eq(searchTitle) : null;
	}

	private BooleanExpression betweenPrice(Integer minPrice, Integer maxPrice) {
		if (minPrice == null && maxPrice == null) {
			return null;
		} else if (minPrice == null) {
			return book.price.amount.loe(maxPrice);
		} else if (maxPrice == null) {
			return book.price.amount.goe(minPrice);
		}
		return book.price.amount.between(minPrice, maxPrice);
	}
}
