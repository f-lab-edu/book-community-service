package com.bookservice.author.service;

import com.bookservice.author.dto.request.AuthorRegisterRequest;
import com.bookservice.author.entity.Author;
import com.bookservice.author.repository.AuthorRepository;
import com.bookservice.common.exception.BookException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookservice.common.exception.ErrorCode.ALREADY_EXIST_AUTHORNAME;

@Service
@RequiredArgsConstructor
public class AuthorService {

	private final AuthorRepository authorRepository;

	@Transactional
	public void registerAuthor(AuthorRegisterRequest request) {
		if(authorRepository.existsByName(request.getName())){
			throw new BookException(ALREADY_EXIST_AUTHORNAME);
		}
		Author author = new Author(request.getName());
		authorRepository.save(author);
	}
}
