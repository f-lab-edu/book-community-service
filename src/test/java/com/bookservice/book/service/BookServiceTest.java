package com.bookservice.book.service;

import com.bookservice.author.entity.Author;
import com.bookservice.author.repository.AuthorRepository;
import com.bookservice.book.dto.request.BookRegisterRequest;
import com.bookservice.book.dto.request.BookUpdateRequest;
import com.bookservice.book.dto.response.BookResponse;
import com.bookservice.book.entity.Book;
import com.bookservice.book.repository.BookHashTagRepository;
import com.bookservice.book.repository.BookRepository;
import com.bookservice.common.exception.BookException;
import com.bookservice.hashtag.entity.HashTag;
import com.bookservice.hashtag.repository.HashTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bookservice.book.fixture.BookFixture.*;
import static com.bookservice.common.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	@InjectMocks
	private BookService bookService;
	@Mock
	private AuthorRepository authorRepository;
	@Mock
	private BookRepository bookRepository;
	@Mock
	private HashTagRepository hashTagRepository;
	@Mock
	private BookHashTagRepository bookHashTagRepository;
	
	@Test
	@DisplayName("책_생성_성공_모든_정보_정상")
	public void 책_생성_성공_모든_정보_정상(){
	    //given
		List<String> hashTagNames = List.of(HASH_01, HASH_02);
		List<HashTag> hashTags = List.of(
				new HashTag(HASH_01),
				new HashTag(HASH_02)
		);
		BookRegisterRequest request = new BookRegisterRequest(TITLE, THUMBNAIL, DESCRIPTION, RELEASE_DATE, AUTHOR, hashTagNames);
		Author author = new Author(AUTHOR);

		given(authorRepository.findByName(AUTHOR)).willReturn(Optional.of(author));
		given(hashTagRepository.findAllByNameIn(anyList())).willReturn(hashTags);

		//when
		bookService.registerBook(request);

		//then
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	@DisplayName("책_등록_실패_작가_이름_없음")
	public void 책_등록_실패_작가_이름_없음(){
		//given
		List<String> hashTagNames = List.of(HASH_01, HASH_02);
		BookRegisterRequest request = new BookRegisterRequest(TITLE, THUMBNAIL, DESCRIPTION, RELEASE_DATE, AUTHOR, hashTagNames);

		//when
		given(authorRepository.findByName(anyString())).willReturn(Optional.empty());

		//then
		assertThatThrownBy(() -> bookService.registerBook(request))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", NOT_FOUND_AUTHOR);
	}

	@Test
	@DisplayName("책_등록_실패_등록된_해시태그_없음")
	public void 책_등록_실패_등록된_해시태그_없음(){
		//given
		Author author = new Author(AUTHOR);
		List<String> hashTagNames = List.of(HASH_01, HASH_02);
		BookRegisterRequest request = new BookRegisterRequest(TITLE, THUMBNAIL, DESCRIPTION, RELEASE_DATE, AUTHOR, hashTagNames);

		//when
		given(authorRepository.findByName(AUTHOR)).willReturn(Optional.of(author));
		given(hashTagRepository.findAllByNameIn(anyList())).willReturn(Collections.emptyList());

		//then
		assertThatThrownBy(() -> bookService.registerBook(request))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", NOT_FOUND_HASH_TAG);
	}

	@Test
	@DisplayName("책_등록_실패_제목_없음")
	public void 책_등록_실패_제목_없음(){
		//given
		List<String> hashTagNames = List.of(HASH_01, HASH_02);
		BookRegisterRequest request = new BookRegisterRequest(null, THUMBNAIL, DESCRIPTION, RELEASE_DATE, AUTHOR, hashTagNames);

		//given & when
		assertThatThrownBy(() -> bookService.registerBook(request))
				.isInstanceOf(BookException.class);
	}
	
	@Test
	@DisplayName("책_수정_성공")
	public void 책_수정_성공(){
	    //given
		Long bookId = 1L;
		List<String> newTagNames = List.of("코미디", "로맨스");
		BookUpdateRequest updateRequest = new BookUpdateRequest("변경된 타이틀1",
				"변경된 썸네일1",
				"변경된 설명1",
				newTagNames
		);

		Book book = toExistsBook(TITLE, THUMBNAIL, DESCRIPTION);
		List<HashTag> newHashTags = List.of(
				new HashTag("코미디"),
				new HashTag("로맨스")
		);

		given(bookRepository.findById(bookId)).willReturn(Optional.of(book));
		given(hashTagRepository.findAllByNameIn(anyList())).willReturn(newHashTags);

		//when
		bookService.updateBook(bookId, updateRequest);
	    
		//then
		assertThat(book.getTitle()).isEqualTo(updateRequest.getTitle());
		assertThat(book.getThumbnail()).isEqualTo(updateRequest.getThumbnail());
		assertThat(book.getDescription()).isEqualTo(updateRequest.getDescription());
	}

	@Test
	@DisplayName("책_수정_실패_아이디_없음")
	public void 책_수정_실패_아이디_없음(){
	    //given
		Long compareBookId = 9999L;
		List<String> newTagNames = List.of("코미디", "로맨스");
		BookUpdateRequest updateRequest = new BookUpdateRequest("변경된 타이틀1",
				"변경된 썸네일1",
				"변경된 설명1",
				newTagNames
		);

		given(bookRepository.findById(compareBookId)).willReturn(Optional.empty());

		//when & then
		assertThatThrownBy(() -> bookService.updateBook(compareBookId, updateRequest))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", NOT_FOUND_BOOK);

		verify(bookHashTagRepository, never()).deleteByBookId(any());
		verify(bookHashTagRepository, never()).saveAll(anyList());
	}
	
	@Test
	@DisplayName("책_수정_실패_등록되지_않은_해시태그")
	public void 책_수정_실패_등록되지_않은_해시태그(){
	    //given
		Long compareBookId = 1L;
		Book existsBook = toExistsBook(TITLE, THUMBNAIL, DESCRIPTION);
		List<String> newTagNames = List.of("코미디", "로맨스");
		BookUpdateRequest updateRequest = new BookUpdateRequest("변경된 타이틀1",
				"변경된 썸네일1",
				"변경된 설명1",
				newTagNames
		);

		given(bookRepository.findById(anyLong())).willReturn(Optional.of(existsBook));
		given(hashTagRepository.findAllByNameIn(updateRequest.getHashTags())).willReturn(Collections.emptyList());

	    //when & then
		assertThatThrownBy(() -> bookService.updateBook(compareBookId, updateRequest))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", NOT_FOUND_HASH_TAG);
	}

	@Test
	@DisplayName("책_삭제_완료")
	public void 책_삭제_성공(){
		//given & when & then
		bookService.deleteBook(anyLong());
	}

	@Test
	@DisplayName("책_조회_완료")
	public void 책_단일_조회_완료(){
	    //given
		Long compareBookId = 1L;
		Author author = new Author(AUTHOR);
		Book existsBook = new Book(TITLE, THUMBNAIL, DESCRIPTION, RELEASE_DATE ,author);

		given(bookRepository.findByIdWithHashTags(anyLong())).willReturn(Optional.of(existsBook));

		//when
		BookResponse resultBook = bookService.getBookInfo(compareBookId);

		//then
		assertThat(resultBook.getTitle()).isEqualTo(existsBook.getTitle());

		verify(bookRepository, times(1)).updateViews(compareBookId);
		verify(bookRepository, times(1)).findByIdWithHashTags(compareBookId);
	}

	@Test
	@DisplayName("책_조회_실패_아이디_없음")
	public void 책_조회_실패_아이디_없음(){
	    //given
		Long compareBookId = 1L;

	    //when
		given(bookRepository.findByIdWithHashTags(anyLong())).willReturn(Optional.empty());

	    //then
		assertThatThrownBy(() -> bookService.getBookInfo(compareBookId))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", NOT_FOUND_BOOK);
	}
}
