package io.jungmini.sample.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserResponse(
	String id,
	@JsonProperty("kakao_account")
	KakaoAccount kakaoAccount
) {
	public record KakaoAccount(
		String email
	) {
	}
}