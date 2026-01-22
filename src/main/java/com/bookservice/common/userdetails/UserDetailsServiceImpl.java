package com.bookservice.common.userdetails;

import com.bookservice.common.exception.BookException;
import com.bookservice.common.exception.ErrorCode;
import com.bookservice.member.entity.Member;
import com.bookservice.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email).orElseThrow(
				() -> new BookException(ErrorCode.NOT_FOUND_EMAIL));
		return new UserDetailsImpl(member, member.getEmail());
	}
}
