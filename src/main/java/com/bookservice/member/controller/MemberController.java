package com.bookservice.member.controller;

import com.bookservice.common.response.SuccessMessage;
import com.bookservice.member.dto.request.MemberSignUpRequest;
import com.bookservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/sign-up")
	public ResponseEntity<SuccessMessage<Void>> signUp(@RequestBody MemberSignUpRequest member) {
		memberService.signUp(member);
		return new ResponseEntity<>(new SuccessMessage<>("회원가입성공",null), HttpStatus.CREATED);
	}
}
