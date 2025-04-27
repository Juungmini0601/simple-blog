package io.jungmini.sample.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jungmini.sample.domain.user.application.UserService;
import io.jungmini.sample.domain.user.dto.UserRegistrationRequest;
import io.jungmini.sample.domain.user.dto.UserResponse;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.annotation.Auth;
import io.jungmini.sample.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerSpec {

	private final UserService userService;

	@PostMapping("/api/users")
	public ApiResponse<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
		User registeredUser = userService.register(
			request.email(),
			request.password()
		);

		UserResponse response = UserResponse.from(registeredUser);
		return ApiResponse.success(response);
	}

	@GetMapping("/api/users/me")
	public ApiResponse<UserResponse> getMyInfo(@Auth User user) {
		UserResponse response = UserResponse.from(user);
		return ApiResponse.success(response);
	}
}