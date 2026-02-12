package com.bookservice.orders.domain;

import com.bookservice.common.time.TimeStamped;
import com.bookservice.orders.domain.enums.PaymentStatus;
import com.bookservice.orders.domain.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import static com.bookservice.orders.domain.enums.PaymentStatus.COMPLETED;

@Entity
@Getter
@NoArgsConstructor
public class Payment extends TimeStamped {
	@Id
	@Column(name = "payment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long finalAmount;

	@NotNull
	@ColumnDefault("0")
	private Long discountAmount;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	private Long transactionId;

	private LocalDateTime paidAt;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Builder
	public Payment(Long finalAmount, Long discountAmount, PaymentType paymentType) {
		this.finalAmount = finalAmount;
		this.discountAmount = discountAmount;
		this.paidAt = LocalDateTime.now();
		this.paymentType = paymentType;
		this.paymentStatus = COMPLETED;
	}

	public static Payment createPayment(Long totalPrice, Long discountAmount, PaymentType paymentType) {
		return Payment.builder()
					   .finalAmount(totalPrice)
					   .discountAmount(discountAmount)
					   .paymentType(paymentType)
					   .build();
	}
}
