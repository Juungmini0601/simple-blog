package io.jungmini.sample.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.jungmini.sample.domain.user.dto.UserRegistrationRequest;
import io.jungmini.sample.domain.user.dto.UserResponse;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "사용자 API", description = "사용자 관련 API")
public interface UserControllerSpec {

	@Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
	@PostMapping("/api/users")
	ApiResponse<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request);

	@Operation(summary = "내 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
	@GetMapping("/api/users/me")
	ApiResponse<UserResponse> getMyInfo(@Parameter(hidden = true) User user);
}
