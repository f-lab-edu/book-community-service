package com.bookservice.coupon.entity;

import com.bookservice.common.exception.BookException;
import com.bookservice.common.time.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

import static com.bookservice.common.exception.ErrorCode.CANT_ISSUED_COUPON;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Coupon extends TimeStamped {
	@Id
	@Column(name = "coupon_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String name;

	@ColumnDefault("1")
	private Integer amount;

	@ColumnDefault("0")
	private Long discountValue;

//	@NotNull
	private LocalDateTime endDate;

	@Builder
	public Coupon(String name, Integer amount, Long discountValue) {
		this.name = name;
		this.amount = amount;
		this.discountValue = discountValue;
	}

	public void update(String name, Integer amount, Long discountValue) {
		this.name = name;
		this.amount = amount;
		this.discountValue = discountValue;
	}

	public void canIssuedCouponCheck() {
		if(this.amount > 0){
			this.amount = amount-1;
		}
		else{
			throw new BookException(CANT_ISSUED_COUPON);
		}
	}
}
