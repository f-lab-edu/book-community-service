package com.bookservice.member.dto.request;

import com.bookservice.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignUpRequest {
	private String email;
	private String password;
	private String nickName;

	public Member toMember(MemberSignUpRequest request) {
		return Member.builder()
				.email(request.getEmail())
				.password(request.getPassword())
				.nickName(request.getNickName())
				.build();
	}
}
