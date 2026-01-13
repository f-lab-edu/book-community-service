package com.bookservice.hashtag.repository;

import com.bookservice.hashtag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
	List<HashTag> findAllByNameIn(List<String> names);
}
