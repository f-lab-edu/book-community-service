package com.bookservice.author.service;

import com.bookservice.author.dto.request.AuthorRegisterRequest;
import com.bookservice.author.entity.Author;
import com.bookservice.author.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

	private final AuthorRepository authorRepository;

	@Transactional
	public void registerAuthor(AuthorRegisterRequest request) {
		Author author = request.toAuthor();
		authorRepository.save(author);
	}
}
