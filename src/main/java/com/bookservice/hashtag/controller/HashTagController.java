package com.bookservice.hashtag.controller;

import com.bookservice.common.response.SuccessMessage;
import com.bookservice.hashtag.dto.request.HashTagRegisterRequest;
import com.bookservice.hashtag.dto.request.HashTagUpdateRequest;
import com.bookservice.hashtag.dto.response.HashTagInfo;
import com.bookservice.hashtag.service.HashTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hash-tag")
public class HashTagController {

	private final HashTagService hashTagService;

	@PostMapping
	public ResponseEntity<SuccessMessage<Void>> registerHashTag(@Valid @RequestBody HashTagRegisterRequest request){
		hashTagService.registerHashTag(request);
		return new ResponseEntity<>(new SuccessMessage<>("해시태그등록성공", null), HttpStatus.CREATED);
	}

	@PutMapping("/{hashTagId}")
	public ResponseEntity<SuccessMessage<Void>> updateHashTag(@PathVariable Long hashTagId, @Valid @RequestBody HashTagUpdateRequest request){
		hashTagService.updateHashTag(hashTagId, request);
		return new ResponseEntity<>(new SuccessMessage<>("해시태그수정성공", null), HttpStatus.OK);
	}

	@DeleteMapping("/{hashTagId}")
	public ResponseEntity<SuccessMessage<Void>> deleteHashTag(@PathVariable Long hashTagId){
		hashTagService.deleteHashTag(hashTagId);
		return new ResponseEntity<>(new SuccessMessage<>("해시태그삭제성공", null), HttpStatus.OK);
	}

	@GetMapping("/{hashTagId}")
	public ResponseEntity<SuccessMessage<Optional<HashTagInfo>>> getHashTagInfo(@PathVariable Long hashTagId){
		Optional<HashTagInfo> response = hashTagService.getHashTagInfo(hashTagId);
		return new ResponseEntity<>(new SuccessMessage<>("해당해시태그조회성공", response), HttpStatus.OK);
	}
}
