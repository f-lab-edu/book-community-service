package com.bookservice.orders.controller;

import com.bookservice.common.response.SuccessMessage;
import com.bookservice.common.userdetails.UserDetailsImpl;
import com.bookservice.orders.dto.request.OrdersRegisterRequest;
import com.bookservice.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrdersController {
	private final OrdersService orderService;

	@PostMapping("/{bookId}")
	public ResponseEntity<SuccessMessage<Void>> purchaseBook(@AuthenticationPrincipal UserDetailsImpl userDetails,
															 @PathVariable Long bookId,
															 @RequestBody OrdersRegisterRequest request){
		orderService.purchaseBook(userDetails, request, bookId);
		return new ResponseEntity<>(new SuccessMessage<>("책구매성공", null), HttpStatus.CREATED);
	}
}
