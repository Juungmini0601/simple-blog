package io.jungmini.sample.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
@Configuration
@ConfigurationPropertiesScan
public class OAuthConfig {

	@Getter
	@RequiredArgsConstructor
	@ConfigurationProperties(prefix = "oauth2.kakao")
	public static class KakaoOAuthProperties {
		private final String authServerBaseUrl;
		private final String resourceServerBaseUrl;
		private final String clientId;
		private final String redirectUri;
	}

	@Getter
	@RequiredArgsConstructor
	@ConfigurationProperties(prefix = "oauth2.google")
	public static class GoogleOAuthProperties {
		private final String baseUrl;
		private final String redirectUri;
		private final String clientId;
		private final String clientSecret;
	}
}

