package io.jungmini.sample.domain.post.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import io.jungmini.sample.domain.post.model.entity.Post;
import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 27.
 */
@Repository
@RequiredArgsConstructor
public class JdbcPostRepository implements PostRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<Post> postRowMapper = (ResultSet rs, int rowNum) -> {
		return Post.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.content(rs.getString("content"))
			.userId(rs.getLong("user_id"))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
			.build();
	};

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM posts WHERE id = :id";

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		jdbcTemplate.update(sql, params);
	}

	@Override
	public Post save(Post post) {
		if (post.getId() == null) {
			// 새 게시글 등록
			String sql = "INSERT INTO posts (title, content, user_id, created_at, updated_at) " +
				"VALUES (:title, :content, :userId, :createdAt, :updatedAt)";

			MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("title", post.getTitle())
				.addValue("content", post.getContent())
				.addValue("userId", post.getUserId())
				.addValue("createdAt", Timestamp.valueOf(getTimeOrNow(post.getCreatedAt())))
				.addValue("updatedAt", Timestamp.valueOf(getTimeOrNow(post.getUpdatedAt())));

			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(sql, params, keyHolder, new String[] {"id"});

			Long newId = keyHolder.getKey().longValue();

			return Post.builder()
				.id(newId)
				.title(post.getTitle())
				.content(post.getContent())
				.userId(post.getUserId())
				.createdAt(post.getCreatedAt() != null ? post.getCreatedAt() : LocalDateTime.now())
				.updatedAt(post.getUpdatedAt() != null ? post.getUpdatedAt() : LocalDateTime.now())
				.build();
		}

		// 기존 게시글 업데이트
		String sql = "UPDATE posts SET title = :title, content = :content, updated_at = :updatedAt " +
			"WHERE id = :id";

		Map<String, Object> params = new HashMap<>();
		params.put("title", post.getTitle());
		params.put("content", post.getContent());
		params.put("updatedAt", Timestamp.valueOf(LocalDateTime.now()));
		params.put("id", post.getId());

		jdbcTemplate.update(sql, params);

		return findById(post.getId()).orElse(post);

	}

	@Override
	public Optional<Post> findById(Long id) {
		String sql = "SELECT id, title, content, user_id, created_at, updated_at " +
			"FROM posts WHERE id = :id";

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		return jdbcTemplate.query(sql, params, postRowMapper)
			.stream()
			.findFirst();
	}

	@Override
	public List<Post> findAllByOrderByCreatedAtDesc() {
		String sql = "SELECT id, title, content, user_id, created_at, updated_at " +
			"FROM posts ORDER BY created_at DESC";

		return jdbcTemplate.query(sql, postRowMapper);
	}

	// 내부 도우미 메서드
	private LocalDateTime getTimeOrNow(LocalDateTime time) {
		return time != null ? time : LocalDateTime.now();
	}

}
