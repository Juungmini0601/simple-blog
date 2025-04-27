package io.jungmini.sample.domain.post.dto;

import java.time.LocalDateTime;

import io.jungmini.sample.domain.post.model.entity.Post;

public record PostResponse(
	Long id,
	String title,
	String content,
	Long userId,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static PostResponse from(Post post) {
		return new PostResponse(
			post.getId(),
			post.getTitle(),
			post.getContent(),
			post.getUserId(),
			post.getCreatedAt(),
			post.getUpdatedAt()
		);
	}
}