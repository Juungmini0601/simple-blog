package io.jungmini.sample.domain.post.dao;

import java.util.List;
import java.util.Optional;

import io.jungmini.sample.domain.post.model.entity.Post;
import io.jungmini.sample.global.error.ErrorType;
import io.jungmini.sample.global.error.MyBlogException;

public interface PostRepository {

	void deleteById(Long id);

	Post save(Post post);

	Optional<Post> findById(Long id);

	List<Post> findAllByOrderByCreatedAtDesc();

	default Post findByIdOrElseThrow(Long id) {
		return findById(id)
			.orElseThrow(() -> new MyBlogException(ErrorType.VALIDATION_ERROR, "존재하지 않는 게시글입니다."));
	}
}