package io.jungmini.sample.domain.user.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import io.jungmini.sample.domain.auth.model.Role;
import io.jungmini.sample.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 27.
 */
@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<User> userRowMapper = (rs, rowNum) -> User.builder()
		.id(rs.getLong("id"))
		.email(rs.getString("email"))
		.password(rs.getString("password"))
		.role(Role.valueOf(rs.getString("role")))
		.build();

	@Override
	public Optional<User> findById(Long id) {
		String sql = "SELECT id, email, password, role FROM users WHERE id = :id";
		return jdbcTemplate.query(sql, Map.of("id", id), userRowMapper).stream().findFirst();
	}

	@Override
	public User save(User user) {
		if (user.getId() == null) {
			String sql = "INSERT INTO users (email, password, role) VALUES (:email, :password, :role)";

			MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("email", user.getEmail())
				.addValue("password", user.getPassword())
				.addValue("role", user.getRole().name());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(sql, params, keyHolder, new String[] {"id"});

			Long newId = keyHolder.getKey().longValue();
			user.setId(newId);

			return user;
		}
		// 기존 사용자 업데이트
		String sql = "UPDATE users SET email = :email, password = :password, role = :role WHERE id = :id";

		Map<String, Object> params = new HashMap<>();
		params.put("email", user.getEmail());
		params.put("password", user.getPassword());
		params.put("role", user.getRole().name());
		params.put("id", user.getId());

		jdbcTemplate.update(sql, params);
		return user;
	}

	@Override
	public boolean existsByEmail(String email) {
		return findByEmail(email).isPresent();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		String sql = "SELECT id, email, password, role FROM users WHERE email = :email";

		Map<String, Object> params = new HashMap<>();
		params.put("email", email);

		return jdbcTemplate.query(sql, params, userRowMapper)
			.stream()
			.findFirst();
	}
}
