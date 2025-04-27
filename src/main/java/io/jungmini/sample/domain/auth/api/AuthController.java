package io.jungmini.sample.domain.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jungmini.sample.domain.auth.application.AuthService;
import io.jungmini.sample.domain.auth.application.OauthClient;
import io.jungmini.sample.domain.auth.application.OauthClientFactory;
import io.jungmini.sample.domain.auth.dto.LoginRequest;
import io.jungmini.sample.domain.auth.dto.OauthResponse;
import io.jungmini.sample.domain.auth.model.OAuthProvider;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpec {

	private final AuthService authService;
	private final OauthClientFactory oauthClientFactory;

	@PostMapping("/api/auth/login")
	public ApiResponse<String> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
		User user = authService.login(loginRequest.email(), loginRequest.password());
		session.setAttribute("LOGIN_USER", user);

		return ApiResponse.success("로그인 성공");
	}

	// 인가 코드 받아오기
	@GetMapping("/api/auth/{provider}/login")
	public void redirectOauthLoginPage(
		@PathVariable("provider") String providerName, // TODO Enum을 바로 등록 받을 수 있다고 하는데, 개선 할 수 있을 거 같음
		HttpServletResponse httpServletResponse) throws Exception {
		OAuthProvider provider = OAuthProvider.fromString(providerName);
		OauthClient client = oauthClientFactory.getClient(provider);
		String redirectUrl = client.loginPageUrl();

		httpServletResponse.sendRedirect(redirectUrl);
	}

	@GetMapping("/api/auth/{provider}/login/callback")
	public ApiResponse<String> oauthLoginCallback(
		@PathVariable("provider") String providerName,
		@RequestParam String code,
		HttpSession session) {
		OAuthProvider provider = OAuthProvider.fromString(providerName);
		OauthClient client = oauthClientFactory.getClient(provider);
		OauthResponse oauthResponse = client.getUserInfo(code);

		User user = authService.oauth2Login(
			oauthResponse.email(),
			oauthResponse.providerId(),
			oauthResponse.provider()
		);

		session.setAttribute("LOGIN_USER", user);
		return ApiResponse.success(String.format("%s 로그인 성공", providerName));
	}
}
