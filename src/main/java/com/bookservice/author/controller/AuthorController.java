package com.bookservice.author.controller;

import com.bookservice.author.dto.request.AuthorRegisterRequest;
import com.bookservice.author.service.AuthorService;
import com.bookservice.common.response.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {

	private final AuthorService authorService;

	@PostMapping
	public ResponseEntity<SuccessMessage<Void>> registerAuthor(@Valid @RequestBody AuthorRegisterRequest request){
		authorService.registerAuthor(request);
		return new ResponseEntity<>(new SuccessMessage<>("작가등록성공", null), HttpStatus.CREATED);
	}
}
