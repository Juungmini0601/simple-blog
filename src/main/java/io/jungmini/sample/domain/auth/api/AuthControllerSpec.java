package io.jungmini.sample.domain.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.jungmini.sample.domain.auth.dto.LoginRequest;
import io.jungmini.sample.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 27.
 */
@Tag(name = "인증 API", description = "인증 관련 API")
public interface AuthControllerSpec {
	@Operation(summary = "로그인")
	@PostMapping("/api/auth/login")
	ApiResponse<String> login(
		@Valid @RequestBody LoginRequest loginRequest,
		@Parameter(hidden = true) HttpSession session);

	@Operation(summary = "OAuth 로그인 페이지로 리다이렉트", description = "지정된 OAuth 제공자의 로그인 페이지로 리다이렉트합니다.")
	@GetMapping("/api/auth/{provider}/login")
	void redirectOauthLoginPage(
		@Parameter(description = "OAuth 제공자 이름 (예: google, kakao)") @PathVariable("provider") String providerName,
		@Parameter(hidden = true) HttpServletResponse httpServletResponse) throws Exception;

	@Operation(summary = "OAuth 로그인 콜백 처리 (직접 호출 X)", description = "OAuth 제공자로부터 받은 인증 코드를 사용하여 로그인을 완료합니다.")
	@GetMapping("/api/auth/{provider}/login/callback")
	ApiResponse<String> oauthLoginCallback(
		@Parameter(description = "OAuth 제공자 이름 (예: google, kakao)") @PathVariable("provider") String providerName,
		@Parameter(description = "OAuth 제공자로부터 받은 인증 코드") @RequestParam String code,
		@Parameter(hidden = true) HttpSession session);

}
