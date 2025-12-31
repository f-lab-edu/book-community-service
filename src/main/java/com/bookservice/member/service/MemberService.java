package com.bookservice.member.service;

import com.bookservice.common.exception.BookException;
import com.bookservice.member.dto.request.MemberSignUpRequest;
import com.bookservice.member.entity.Member;
import com.bookservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookservice.common.exception.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.bookservice.common.exception.ErrorCode.ALREADY_EXIST_NICKNAME;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public void signUp(MemberSignUpRequest request) {
		if(memberRepository.existsByEmail(request.getEmail())){
			throw new BookException(ALREADY_EXIST_EMAIL);
		}
		if(memberRepository.existsByNickName(request.getNickName())){
			throw new BookException(ALREADY_EXIST_NICKNAME);
		}
		String encodePassword = passwordEncoder.encode(request.getPassword());
		Member member = request.toMember(encodePassword);
		memberRepository.save(member);
	}
}
