package com.bookservice.member.entity;

import com.bookservice.common.time.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
		@UniqueConstraint(
				name = "uk_member_email_nickname",
				columnNames = {"email", "nickname"}
		)
})
public class Member extends TimeStamped {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String nickName;

	@Builder
	public Member(Long id, String email, String password, String nickName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
	}
}
