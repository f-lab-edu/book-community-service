package com.bookservice.book.entity;

import com.bookservice.common.exception.BookException;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

import static com.bookservice.common.exception.ErrorCode.CHECK_RATING;

@Embeddable
public class Rating {
	@ColumnDefault("1")
	private Double averageRating;

	public Rating isRating(Double averageRating){
		Rating rating = new Rating();
		if(averageRating <= 1 || averageRating >= 5){
			throw new BookException(CHECK_RATING);
		}
		return rating;
	}
}
