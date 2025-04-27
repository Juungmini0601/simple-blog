package io.jungmini.sample.domain.auth.dto;

public record KakaoTokenResponse(
	String access_token,
	String refresh_token,
	String token_type,
	int expires_in,
	String scope
) {
}