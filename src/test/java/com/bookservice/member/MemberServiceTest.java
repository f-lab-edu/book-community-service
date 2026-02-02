package com.bookservice.member;

import com.bookservice.common.exception.BookException;
import com.bookservice.common.session.LoginService;
import com.bookservice.member.dto.request.MemberLoginRequest;
import com.bookservice.member.dto.request.MemberSignUpRequest;
import com.bookservice.member.entity.Member;
import com.bookservice.member.fixture.MemberFixture;
import com.bookservice.member.repository.MemberRepository;
import com.bookservice.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_EMAIL;
import static com.bookservice.common.exception.ErrorCode.NOT_VALID_PASSWORD;
import static com.bookservice.member.fixture.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
	@InjectMocks
	private MemberService memberService;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private LoginService loginService;

	@Test
	@DisplayName("회원가입시_성공_패스워드_암호화_성공")
	public void 회원가입시_성공_패스워드_암호화_성공(){
		//given
		MemberSignUpRequest request = new MemberSignUpRequest(USER_EMAIL, PASSWORD, NICKNAME);

		//when
		memberService.signUp(request);

		//then
		verify(memberRepository, times(1)).save(any(Member.class));
		verify(passwordEncoder, times(1)).encode(PASSWORD);
	}
	
	@Test
	@DisplayName("로그인시_성공_패스워드_일치_세션_생성")
	public void 로그인시_성공_패스워드_일치_세션_생성(){
		//given
		String encode_password = "encode_password";
		Member member = MemberFixture.toMember(USER_EMAIL, encode_password, NICKNAME);
		MemberLoginRequest request = new MemberLoginRequest(USER_EMAIL, PASSWORD);

		given(memberRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(member));
		given(passwordEncoder.matches(PASSWORD, member.getPassword())).willReturn(true);

		//when
		memberService.login(request);

		//then
		verify(loginService, times(1)).login(USER_EMAIL);
	}

	@Test
	@DisplayName("로그인_실패_이메일_불일치")
	public void 로그인_실패_이메일_불일치(){
	    //given
		MemberLoginRequest request = new MemberLoginRequest("other_user_email", PASSWORD);

		given(memberRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());
		//when
		assertThatThrownBy(() -> memberService.login(request))
				.isInstanceOf(BookException.class)
				.hasFieldOrPropertyWithValue("errorCode", NOT_FOUND_EMAIL);
	}

	@Test
	@DisplayName("로그인_실패_비밀번호_불일치")
	public void 로그인_실패_비밀번호_불일치(){
		//given
		Member member = MemberFixture.toMember(USER_EMAIL, PASSWORD, NICKNAME);
		MemberLoginRequest request = new MemberLoginRequest(USER_EMAIL, "encode_password");

		given(memberRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(member));
		given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

		//when&then
		assertThatThrownBy(() ->  memberService.login(request))
			.isInstanceOf(BookException.class)
			.hasFieldOrPropertyWithValue("errorCode", NOT_VALID_PASSWORD);
	}

	@Test
	@DisplayName("로그아웃_성공")
	public void 로그아웃_성공(){
		//when
		loginService.logout();

		//then
		verify(loginService, times(1)).logout();
	}
}
