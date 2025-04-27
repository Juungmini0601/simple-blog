package io.jungmini.sample.domain.user.model.entity;

import io.jungmini.sample.domain.auth.model.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	private Long id;

	private String email;

	private String password;

	private Role role = Role.USER;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}