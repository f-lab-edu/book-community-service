package com.bookservice.hashtag.dto.response;

import com.bookservice.hashtag.entity.HashTag;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HashTagInfo {
	private final Long hashTagId;
	private final String name;

	@Builder
	public HashTagInfo(Long hashTagId, String name) {
		this.hashTagId = hashTagId;
		this.name = name;
	}

	public static HashTagInfo toHashTag(HashTag hashTag) {
		return new HashTagInfo(
				hashTag.getId(),
				hashTag.getName()
		);
	}
}
