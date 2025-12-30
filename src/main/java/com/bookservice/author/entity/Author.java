package com.bookservice.author.entity;

import com.bookservice.common.time.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
		@UniqueConstraint(
				name = "uk_author_name",
				columnNames = {"name"}
		)
})
public class Author extends TimeStamped {
	@Id
	@Column(name = "author_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@Builder
	public Author(String name) {
		this.name = name;
	}

	public void update(String name) {
		this.name = name;
	}
}
