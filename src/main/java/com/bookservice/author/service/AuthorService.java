package com.bookservice.author.service;

import com.bookservice.author.controller.AuthorUpdateRequest;
import com.bookservice.author.dto.request.AuthorRegisterRequest;
import com.bookservice.author.dto.response.AuthorInfo;
import com.bookservice.author.entity.Author;
import com.bookservice.author.repository.AuthorRepository;
import com.bookservice.common.exception.BookException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.bookservice.common.exception.ErrorCode.ALREADY_EXIST_AUTHORNAME;
import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_AUTHOR;

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

	@Transactional
	public void updateAuthor(Long authorId, AuthorUpdateRequest request) {
		Author author = authorRepository.findById(authorId).orElseThrow(
				() -> new BookException(NOT_FOUND_AUTHOR));
		author.update(request.getName());
	}

	@Transactional
	public void deleteAuthor(Long authorId) {
		Author author = authorRepository.findById(authorId).orElseThrow(
				() -> new BookException(NOT_FOUND_AUTHOR));
		authorRepository.deleteById(author.getId());
	}

	@Transactional(readOnly = true)
	public Optional<AuthorInfo> getAuthorInfo(Long authorId) {
		return authorRepository.findById(authorId)
					.map(AuthorInfo::toAuthor);
	}
}
