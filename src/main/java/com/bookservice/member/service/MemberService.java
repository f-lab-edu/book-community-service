package com.bookservice.member.service;

import com.bookservice.common.exception.BookException;
import com.bookservice.common.session.LoginService;
import com.bookservice.member.dto.request.MemberLoginRequest;
import com.bookservice.member.dto.request.MemberSignUpRequest;
import com.bookservice.member.entity.Member;
import com.bookservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_EMAIL;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final LoginService loginService;

	@Transactional
	public void signUp(MemberSignUpRequest request) {
		String encodePassword = passwordEncoder.encode(request.getPassword());
		Member member = request.toMember(encodePassword);
		memberRepository.save(member);
	}

	public void login(MemberLoginRequest request) {
		Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(
				() -> new BookException(NOT_FOUND_EMAIL)
		);

		member.checkDuplicatedEmail(member.getEmail());
		member.checkPassword(passwordEncoder, request.getPassword());

		loginService.login(member.getEmail());
	}

	public void logout(){
		loginService.logout();
	}
}
