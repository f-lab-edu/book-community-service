package com.bookservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
/**
 * 이 클래스는 api 응답 형식이고, Controller에서 ResponseEntity 객체안에 담깁니다.
 * 아래는 형식의 예시 입니다.
 *
 * {
 * "success" :"책 조회 성공",
 * "data" :
 *    {
 *    "bookId" : 1,
 *    "title" : "무소유"
 *    }
 * }
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessage<T> {
	private final String success;
	private final T data;

	public SuccessMessage(String success, T data) {
		this.success = success;
		this.data = data;
	}
}
