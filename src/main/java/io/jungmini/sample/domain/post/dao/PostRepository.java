package io.jungmini.sample.domain.post.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jungmini.sample.domain.post.model.entity.Post;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.error.ErrorType;
import io.jungmini.sample.global.error.MyBlogException;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByOrderByCreatedAtDesc();

	List<Post> findByUserOrderByCreatedAtDesc(User user);

	default Post findByIdOrElseThrow(Long id) {
		return findById(id)
			.orElseThrow(() -> new MyBlogException(ErrorType.VALIDATION_ERROR, "존재하지 않는 게시글입니다."));
	}
}