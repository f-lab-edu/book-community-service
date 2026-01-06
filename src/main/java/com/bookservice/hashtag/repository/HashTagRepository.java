package com.bookservice.hashtag.repository;

import com.bookservice.hashtag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
}
