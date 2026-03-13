package com.bookservice.hashtag.service;

import com.bookservice.common.exception.BookException;
import com.bookservice.hashtag.dto.request.HashTagRegisterRequest;
import com.bookservice.hashtag.dto.request.HashTagUpdateRequest;
import com.bookservice.hashtag.dto.response.HashTagInfo;
import com.bookservice.hashtag.entity.HashTag;
import com.bookservice.hashtag.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_HASH_TAG;

@Service
@RequiredArgsConstructor
public class HashTagService {

	private final HashTagRepository hashTagRepository;

	@Transactional
	public void registerHashTag(HashTagRegisterRequest request) {
		HashTag hashTag = new HashTag(request.getName());
		hashTagRepository.save(hashTag);
	}

	@Transactional
	public void updateHashTag(Long hashTagId, HashTagUpdateRequest request) {
		HashTag hashTag = hashTagRepository.findById(hashTagId).orElseThrow(
				() -> new BookException(NOT_FOUND_HASH_TAG));
		hashTag.update(request.getName());
	}

	@Transactional
	public void deleteHashTag(Long hashTagId) {
		hashTagRepository.findById(hashTagId).orElseThrow(
				() -> new BookException(NOT_FOUND_HASH_TAG));
		hashTagRepository.deleteById(hashTagId);
	}

	@Transactional(readOnly = true)
	public Optional<HashTagInfo> getHashTagInfo(Long hashTagId) {
		return hashTagRepository.findById(hashTagId)
					.map(HashTagInfo::toHashTag);
	}

	public List<HashTag> findAllByNameIn(List<String> newHashTags) {
		return hashTagRepository.findAllByNameIn(newHashTags);
	}

	public void validateAllHashTagsExist(List<HashTag> existsTags, List<String> newHashTags) {
		if (existsTags.size() != newHashTags.size()) {
			throw new BookException(NOT_FOUND_HASH_TAG);
		}
	}
}
