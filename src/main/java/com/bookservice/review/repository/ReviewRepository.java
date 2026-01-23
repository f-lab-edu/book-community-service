package com.bookservice.review.repository;

import com.bookservice.review.entity.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT r FROM Review r " +
				   "JOIN r.book b" +
				   " WHERE b.id = :bookId")
	List<Review> findByBookIdToAllReviews(@Param("bookId") Long bookId);
}
