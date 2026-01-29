package com.bookservice.member.fixture;

import com.bookservice.member.entity.Member;

public class MemberFixture {
	public static final String USER_EMAIL = "test@naver.com";
	public static final String PASSWORD = "test";
	public static final String NICKNAME = "test";

	public static Member toMember(String email, String password, String nickName){
		return Member.builder()
					   .email(email)
					   .password(password)
					   .nickName(nickName)
					   .build();
	}
}
