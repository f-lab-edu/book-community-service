package com.bookservice.member.dto.request;

import com.bookservice.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequest {
	private String email;
	private String password;
	private String nickName;

	public Member toMember(String encodePassword) {
		return Member.builder()
				.email(email)
				.password(encodePassword)
				.nickName(nickName)
				.build();
	}
}
