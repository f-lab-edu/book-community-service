package com.bookservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookException extends RuntimeException {
	private final ErrorCode errorCode;
}
