package io.jungmini.sample.domain.auth.application;

import io.jungmini.sample.domain.auth.dto.OauthResponse;
import io.jungmini.sample.domain.auth.model.OAuthProvider;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
public interface OauthClient {
	OauthResponse getUserInfo(String token);

	String loginPageUrl();

	OAuthProvider getProvider();
}
