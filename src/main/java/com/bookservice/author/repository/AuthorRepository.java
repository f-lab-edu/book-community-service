package com.bookservice.author.repository;

import com.bookservice.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	boolean existsByName(String name);
}
