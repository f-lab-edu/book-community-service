package com.bookservice.book.dto.command;

import com.bookservice.hashtag.entity.HashTag;

import java.util.List;

public record BookUpdateCommand(
		String title,
		String thumbnail,
		String description,
		boolean isFree,
		Long amount,
		List<HashTag> newHashTags
) {
}
