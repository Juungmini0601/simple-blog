package io.jungmini.sample.domain.auth.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import io.jungmini.sample.domain.auth.model.OAuthProvider;
import io.jungmini.sample.domain.auth.model.Role;
import io.jungmini.sample.domain.auth.model.entity.Oauth;
import io.jungmini.sample.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 27.
 */
@Repository
@RequiredArgsConstructor
public class JdbcOauthRepository implements OauthRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
		User user = new User(rs.getString("email"), rs.getString("password"));
		user.setId(rs.getLong("id"));
		user.setRole(Role.valueOf(rs.getString("role")));
		return user;
	};

	@Override
	public Oauth save(Oauth oauth) {
		if (oauth.getId() == null) {
			// 신규 OAuth 정보 등록
			String sql = "INSERT INTO oauth (provider, provider_id, user_id) VALUES (:provider, :providerId, :userId)";

			MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("provider", oauth.getProvider().name())
				.addValue("providerId", oauth.getProviderId())
				.addValue("userId", oauth.getUserId());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(sql, params, keyHolder, new String[] {"id"});

			Long newId = keyHolder.getKey().longValue();
			return Oauth.builder()
				.id(newId)
				.provider(oauth.getProvider())
				.providerId(oauth.getProviderId())
				.userId(oauth.getUserId())
				.build();
		}

		// 기존 OAuth 정보 업데이트
		String sql = "UPDATE oauth SET provider = :provider, provider_id = :providerId, user_id = :userId WHERE id = :id";

		Map<String, Object> params = new HashMap<>();
		params.put("provider", oauth.getProvider().name());
		params.put("providerId", oauth.getProviderId());
		params.put("userId", oauth.getUserId());
		params.put("id", oauth.getId());

		jdbcTemplate.update(sql, params);
		return oauth;

	}

	@Override
	public Optional<User> findByProviderIdAndProvider(String providerId, OAuthProvider oAuthProvider) {
		// OAuth 정보로 user_id를 찾아 User 정보 조회
		String sql = "SELECT u.id, u.email, u.password, u.role " +
			"FROM users u " +
			"JOIN oauth o ON u.id = o.user_id " +
			"WHERE o.provider_id = :providerId AND o.provider = :provider";

		Map<String, Object> params = new HashMap<>();
		params.put("providerId", providerId);
		params.put("provider", oAuthProvider.name());

		return jdbcTemplate.query(sql, params, userRowMapper)
			.stream()
			.findFirst();
	}

}
