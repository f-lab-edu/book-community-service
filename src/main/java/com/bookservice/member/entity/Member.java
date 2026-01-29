package com.bookservice.member.entity;

import com.bookservice.common.exception.BookException;
import com.bookservice.common.exception.ErrorCode;
import com.bookservice.common.time.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

import static com.bookservice.common.exception.ErrorCode.NOT_FOUND_EMAIL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeStamped implements Serializable {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	private String password;

	@NotNull
	@Column(unique = true)
	private String nickName;

	@Builder
	public Member(Long id, String email, String password, String nickName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
	}

	public void checkDuplicatedEmail(String email) {
		if(!this.email.equals(email)){
			throw new BookException(NOT_FOUND_EMAIL);
		}
	}

	public void checkPassword(BCryptPasswordEncoder passwordEncoder, String password) {
		if(!passwordEncoder.matches(password, this.password)){
			throw new BookException(ErrorCode.NOT_VALID_PASSWORD);
		}
	}
}
