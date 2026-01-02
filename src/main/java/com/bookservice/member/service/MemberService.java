package com.bookservice.member.service;

import com.bookservice.member.dto.request.MemberSignUpRequest;
import com.bookservice.member.entity.Member;
import com.bookservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Validated
	@Transactional
	public void signUp(MemberSignUpRequest request) {
		String encodePassword = passwordEncoder.encode(request.getPassword());
		Member member = request.toMember(encodePassword);
		memberRepository.save(member);
	}
}
