package io.jungmini.sample.domain.auth.dto;

import io.jungmini.sample.domain.auth.model.OAuthProvider;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
public record OauthResponse(
	String providerId,
	String email,
	OAuthProvider provider
) {
}
