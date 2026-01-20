package com.bookservice.member.controller;

import com.bookservice.common.response.SuccessMessage;
import com.bookservice.member.dto.request.MemberLoginRequest;
import com.bookservice.member.dto.request.MemberSignUpRequest;
import com.bookservice.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/login")
	public ResponseEntity<SuccessMessage<Void>> login(@RequestBody MemberLoginRequest member, HttpServletRequest reuqest) {
		memberService.login(member);
		return new ResponseEntity<>(new SuccessMessage<>("로그인성공",null), HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity<SuccessMessage<Void>> logout() {
		memberService.logout();
		return new ResponseEntity<>(new SuccessMessage<>("로그아웃성공",null), HttpStatus.OK);
	}
}
