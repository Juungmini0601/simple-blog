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

import io.jungmini.sample.domain.post.dto.PostCreateRequest;
import io.jungmini.sample.domain.post.dto.PostResponse;
import io.jungmini.sample.domain.post.dto.PostUpdateRequest;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "게시글 API", description = "게시글 관련 API")
public interface PostControllerSpec {

	@Operation(summary = "전체 게시글 조회", description = "모든 게시글 목록을 조회합니다.")
	@GetMapping("/api/posts")
	ApiResponse<List<PostResponse>> getAllPosts();

	@Operation(summary = "단일 게시글 조회", description = "특정 ID의 게시글을 조회합니다.")
	@GetMapping("/api/posts/{id}")
	ApiResponse<PostResponse> getPost(@Parameter(description = "게시글 ID") @PathVariable Long id);

	@Operation(summary = "게시글 생성", description = "새 게시글을 작성합니다.")
	@PostMapping("/api/posts")
	@ResponseStatus(HttpStatus.CREATED)
	ApiResponse<PostResponse> createPost(
		@Valid @RequestBody PostCreateRequest request,
		@Parameter(hidden = true) User user
	);

	@Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다. 본인이 작성한 게시글만 수정 가능합니다.")
	@PutMapping("/api/posts/{id}")
	ApiResponse<PostResponse> updatePost(
		@Parameter(description = "게시글 ID") @PathVariable Long id,
		@Valid @RequestBody PostUpdateRequest request,
		@Parameter(hidden = true) User user);

	@Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다. 본인이 작성한 게시글만 삭제 가능합니다.")
	@DeleteMapping("/api/posts/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	ApiResponse<Void> deletePost(
		@Parameter(description = "게시글 ID") @PathVariable Long id,
		@Parameter(hidden = true) User user);
}
