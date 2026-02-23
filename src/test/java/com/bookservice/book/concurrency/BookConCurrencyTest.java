package com.bookservice.book.concurrency;

import com.bookservice.book.dto.response.BookResponse;
import com.bookservice.book.repository.BookRepository;
import com.bookservice.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("local")
@EnableCaching
public class BookConCurrencyTest {
	@Autowired
	private BookService bookService;

	@MockitoBean
	private BookRepository bookRepository;

	@Autowired
	private CacheManager cacheManager;

	@BeforeEach
	void setUp() {
		// 매 테스트 시작 전, 캐시를 완전히 비워 'Cache Miss' 상황을 강제로 만듭니다.
		if (cacheManager.getCache("weeklyBestSellers") != null) {
			cacheManager.getCache("weeklyBestSellers").clear();
		}
	}

	@Test
	@DisplayName("AOP_통해_DB_접속은_한번만_접속한다")
	void AOP_통해_DB_접속은_한번만_접속한다() throws InterruptedException {
		List<BookResponse> dummyResponses = new ArrayList<>();
		dummyResponses.add(BookResponse.builder()
								   .bookId(1L)
								   .title("테스트도서")
								   .viewCount(100)
								   .interestedCount(50)
								   .releaseDate("2026-02-23")
								   .build());

		int threadCount = 100;

		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);
		PageRequest pageRequest = PageRequest.of(0, 10);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					bookService.getBestSellersResponse(pageRequest);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		verify(bookRepository, times(1)).getBestSellersResponse(any());
	}
}
