package com.bookservice.hashtag.dto.response;

import com.bookservice.hashtag.entity.HashTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HashTagInfo {
	private final Long hashTagId;
	private final String name;

	public static HashTagInfo toHashTag(HashTag hashTag) {
		return new HashTagInfo(
				hashTag.getId(),
				hashTag.getName()
		);
	}
}
