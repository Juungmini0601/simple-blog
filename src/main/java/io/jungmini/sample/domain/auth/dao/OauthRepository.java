package io.jungmini.sample.domain.auth.dao;

import java.util.Optional;

import io.jungmini.sample.domain.auth.model.OAuthProvider;
import io.jungmini.sample.domain.auth.model.entity.Oauth;
import io.jungmini.sample.domain.user.model.entity.User;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
public interface OauthRepository {
	Oauth save(Oauth oauth);

	Optional<User> findByProviderIdAndProvider(String providerId, OAuthProvider oAuthProvider);
}
