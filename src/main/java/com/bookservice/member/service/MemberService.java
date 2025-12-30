package com.bookservice.member.service;

import com.bookservice.member.dto.request.MemberSignUpRequest;
import com.bookservice.member.entity.Member;
import com.bookservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public void signUp(MemberSignUpRequest request) {
		Member member = request.toMember(request);
		memberRepository.save(member);
	}
}
