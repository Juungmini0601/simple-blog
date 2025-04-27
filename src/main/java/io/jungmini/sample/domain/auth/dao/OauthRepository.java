package io.jungmini.sample.domain.auth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jungmini.sample.domain.auth.model.OAuthProvider;
import io.jungmini.sample.domain.auth.model.entity.Oauth;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
public interface OauthRepository extends JpaRepository<Oauth, Long> {
	Optional<Oauth> findByProviderIdAndProvider(String providerId, OAuthProvider oAuthProvider);
}
