package io.jungmini.sample.domain.user.dto;

import io.jungmini.sample.domain.auth.model.Role;
import io.jungmini.sample.domain.user.model.entity.User;

public record UserResponse(
	Long id,
	String email,
	Role role
) {
	public static UserResponse from(User user) {
		return new UserResponse(
			user.getId(),
			user.getEmail(),
			user.getRole()
		);
	}
}