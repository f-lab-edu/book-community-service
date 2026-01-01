package com.bookservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "MEMBER_002", "이미 존재하는 이메일입니다."),
	ALREADY_EXIST_NICKNAME(HttpStatus.CONFLICT, "MEMBER_003", "이미 존재하는 닉네임입니다."),

	NOT_FOUND_AUTHOR(HttpStatus.NOT_FOUND, "AUTHOR_001", "찾을 수 없는 작가입니다."),
	ALREADY_EXIST_AUTHORNAME(HttpStatus.BAD_REQUEST, "AUTHOR_002", "이미 존재하는 작가이름입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String error;
}
