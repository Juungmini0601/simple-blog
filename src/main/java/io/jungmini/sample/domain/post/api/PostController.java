package io.jungmini.sample.domain.post.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jungmini.sample.domain.post.application.PostService;
import io.jungmini.sample.domain.post.dto.PostCreateRequest;
import io.jungmini.sample.domain.post.dto.PostResponse;
import io.jungmini.sample.domain.post.dto.PostUpdateRequest;
import io.jungmini.sample.domain.post.model.entity.Post;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.annotation.Auth;
import io.jungmini.sample.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController implements PostControllerSpec {

	private final PostService postService;

	@GetMapping("/api/posts")
	public ApiResponse<List<PostResponse>> getAllPosts() {
		List<Post> posts = postService.findAll();
		List<PostResponse> responses = posts.stream()
			.map(PostResponse::from)
			.toList();

		return ApiResponse.success(responses);
	}

	@GetMapping("/api/posts/{id}")
	public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
		Post post = postService.findById(id);
		return ApiResponse.success(PostResponse.from(post));
	}

	@PostMapping("/api/posts")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request, @Auth User user) {
		Post post = postService.create(
			request.title(),
			request.content(),
			user
		);

		return ApiResponse.success(PostResponse.from(post));
	}

	@PutMapping("/api/posts/{id}")
	public ApiResponse<PostResponse> updatePost(
		@PathVariable Long id,
		@Valid @RequestBody PostUpdateRequest request,
		@Auth User user) {
		Post post = postService.update(
			id,
			request.title(),
			request.content(),
			user
		);

		return ApiResponse.success(PostResponse.from(post));
	}

	@DeleteMapping("/api/posts/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deletePost(@PathVariable Long id, @Auth User user) {
		postService.delete(id, user);

		return ApiResponse.success(null);
	}
}