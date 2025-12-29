package com.bookservice.author.service;

import com.bookservice.author.dto.request.AuthorRegisterRequest;
import com.bookservice.author.entity.Author;
import com.bookservice.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {

	private final AuthorRepository authorRepository;

	@Transactional
	public void registerAuthor(AuthorRegisterRequest request) {
		Author author = new Author(request.getName());
		authorRepository.save(author);
	}
}
