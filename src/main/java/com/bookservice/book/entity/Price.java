package com.bookservice.book.entity;

import com.bookservice.common.exception.BookException;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

import static com.bookservice.common.exception.ErrorCode.CHECK_FREE_BOOK;
import static com.bookservice.common.exception.ErrorCode.CHECK_PAID_BOOK;

@Embeddable
public class Price {
	@ColumnDefault("true")
	private Boolean isFree;

	@ColumnDefault("0")
	private Integer amount;

	public Price isFree(Integer amount){
		if(amount > 0){
			throw new BookException(CHECK_FREE_BOOK);
		}
		Price price = new Price();
		price.isFree = true;
		price.amount = 0;
		return price;
	}

	public Price isPaid(Integer amount){
		if(amount <= 0){
			throw new BookException(CHECK_PAID_BOOK);
		}
		Price price = new Price();
		price.isFree = false;
		price.amount = amount;
		return price;
	}
}
