package io.jungmini.sample.domain.auth.model;

import io.jungmini.sample.global.error.ErrorType;
import io.jungmini.sample.global.error.MyBlogException;

public enum OAuthProvider {
	KAKAO,
	NAVER,
	GOOGLE,
	GITHUB;

	public static OAuthProvider fromString(String provider) {
		return switch (provider.toUpperCase()) {
			case "KAKAO" -> KAKAO;
			case "NAVER" -> NAVER;
			case "GOOGLE" -> GOOGLE;
			case "GITHUB" -> GITHUB;

			default -> throw new MyBlogException(
				ErrorType.NOT_SUPPORTED_PROVIDER,
				String.format("%s는 지원하지 않는 oauth 프로바이더 입니다.", provider)
			);
		};
	}
}