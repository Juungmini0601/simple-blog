package io.jungmini.sample.domain.post.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jungmini.sample.domain.post.dao.PostRepository;
import io.jungmini.sample.domain.post.model.entity.Post;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.error.ErrorType;
import io.jungmini.sample.global.error.MyBlogException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public List<Post> findAll() {
		return postRepository.findAllByOrderByCreatedAtDesc();
	}

	@Transactional(readOnly = true)
	public Post findById(Long id) {
		return postRepository.findByIdOrElseThrow(id);
	}

	@Transactional
	public Post create(String title, String content, User user) {
		Post post = Post.builder()
			.title(title)
			.content(content)
			.userId(user.getId())
			.build();

		return postRepository.save(post);
	}

	@Transactional
	public Post update(Long id, String title, String content, User user) {
		Post post = postRepository.findByIdOrElseThrow(id);
		if (!post.isOwner(user.getId())) {
			throw new MyBlogException(ErrorType.AUTHORIZATION_ERROR, "게시글의 작성자만 수정/삭제할 수 있습니다.");
		}

		post.update(title, content);

		return postRepository.save(post);
	}

	@Transactional
	public void delete(Long id, User user) {
		Post post = postRepository.findByIdOrElseThrow(id);
		if (!post.isOwner(user.getId())) {
			throw new MyBlogException(ErrorType.AUTHORIZATION_ERROR, "게시글의 작성자만 수정/삭제할 수 있습니다.");
		}

		postRepository.deleteById(id);
	}
}