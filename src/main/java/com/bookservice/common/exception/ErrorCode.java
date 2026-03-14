package com.bookservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "MEMBER_001", "찾을 수 없는 이메일입니다."),
	ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "MEMBER_002", "이미 존재하는 이메일입니다."),
	ALREADY_EXIST_NICKNAME(HttpStatus.CONFLICT, "MEMBER_003", "이미 존재하는 닉네임입니다."),
	NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_004", "비밀번호를 다시 확인해주세요."),
	FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "MEMBER_005", "접근 권한이 없습니다."),
	ALREADY_EXIST_COUPON(HttpStatus.CONFLICT, "MEMBER_006", "이미 발급받은 쿠폰입니다."),

	NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "BOOK_001", "찾을 수 없는 책입니다."),
	CHECK_FREE_BOOK(HttpStatus.BAD_REQUEST, "BOOK_002", "무료 도서는 가격 입력이 불가합니다."),
	CHECK_PAID_BOOK(HttpStatus.BAD_REQUEST, "BOOK_003", "유료 도서 가격을 입력해주세요."),
	CHECK_RATING(HttpStatus.BAD_REQUEST, "BOOK_004", "별점을 확인해주세요."),

	ALREADY_OWNED_BOOK(HttpStatus.NOT_FOUND, "ORDERED_BOOK_001", "이미 구매한 책입니다."),

	NOT_FOUND_AUTHOR(HttpStatus.NOT_FOUND, "AUTHOR_001", "찾을 수 없는 작가입니다."),
	ALREADY_EXIST_AUTHORNAME(HttpStatus.BAD_REQUEST, "AUTHOR_002", "이미 존재하는 작가이름입니다."),

	NOT_FOUND_HASH_TAG(HttpStatus.NOT_FOUND, "HASH_TAG_001", "찾을 수 없는 해시태그입니다."),
	ALREADY_EXIST_HASH_TAG(HttpStatus.CONFLICT, "HASH_TAG_002", "이미 존재하는 해시태그입니다."),

	NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "REVIEW_001", "찾을 수 없는 리뷰입니다."),

	NOT_FOUND_COUPON(HttpStatus.NOT_FOUND, "COUPON_001", "찾을 수 없는 쿠폰입니다."),
	NO_AVAILABLE_COUPONS(HttpStatus.BAD_REQUEST, "COUPON_002", "사용 할 수 있는 쿠폰이 없습니다."),
	CANT_ISSUED_COUPON(HttpStatus.BAD_REQUEST, "COUPON_003", "쿠폰이 모두 소진됐습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String error;
}
