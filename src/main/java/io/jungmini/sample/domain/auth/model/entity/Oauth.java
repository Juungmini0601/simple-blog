package io.jungmini.sample.domain.auth.model.entity;

import io.jungmini.sample.domain.auth.model.OAuthProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth {

	private Long id;
	private OAuthProvider provider;
	private String providerId;
	private Long userId;

	public Oauth(OAuthProvider provider, String providerId, Long userId) {
		this.provider = provider;
		this.providerId = providerId;
		this.userId = userId;
	}
}