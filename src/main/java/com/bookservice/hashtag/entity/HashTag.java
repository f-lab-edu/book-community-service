package com.bookservice.hashtag.entity;

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
				name = "uk_hash_tag_name",
				columnNames = {"hash_tag_name"}
		)
})
public class HashTag extends TimeStamped {
	@Id
	@Column(name = "hash_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@Builder
	public HashTag(String name){
		this.name = name;
	}

	public void update(String name){
		this.name = name;
	}
}
