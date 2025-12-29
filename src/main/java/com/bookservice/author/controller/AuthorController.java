package com.bookservice.author.controller;

import com.bookservice.author.dto.request.AuthorRegisterRequest;
import com.bookservice.author.service.AuthorService;
import com.bookservice.common.response.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PutMapping("/{authorId}")
	public ResponseEntity<SuccessMessage<Void>> updateAuthor(@PathVariable Long authorId, @Valid @RequestBody AuthorUpdateRequest request){
		authorService.updateAuthor(authorId, request);
		return new ResponseEntity<>(new SuccessMessage<>("작가정보수정성공", null), HttpStatus.OK);
	}

	@DeleteMapping("/{authorId}")
	public ResponseEntity<SuccessMessage<Void>> deleteAuthor(@PathVariable Long authorId){
		authorService.deleteAuthor(authorId);
		return new ResponseEntity<>(new SuccessMessage<>("작가삭제성공", null), HttpStatus.OK);
	}
}
