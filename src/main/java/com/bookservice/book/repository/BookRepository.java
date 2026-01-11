package com.bookservice.book.repository;

import com.bookservice.book.entity.Book;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("select b from Book b join fetch b.bookHashTags bh join fetch bh.hashTag where b.id = :id")
	Optional<Book> findByIdWithHashTags(@Param("id") Long id);

	@Modifying(clearAutomatically = true)
	@Query("update Book b set b.viewCount = b.viewCount + 1 where b.id = :bookId")
	void updateViews(Long bookId);
}
