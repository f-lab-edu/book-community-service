package com.bookservice.member.service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class SessionLoginService implements LoginService{

	private static final String USER_ID = "USER_ID";
	private final HttpSession session;

	@Override
	public void login(String email) {
		session.setAttribute(USER_ID, email);

		setAuthenticationContext(email);
	}

	private void setAuthenticationContext(String email) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
		session.setAttribute("SPRING_SECURITY_CONTEXT", context);
	}

	@Override
	public void logout() {
		SecurityContextHolder.clearContext();
		session.invalidate();
	}
}
