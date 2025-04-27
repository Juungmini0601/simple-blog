package io.jungmini.sample.domain.post.dto;

import java.time.LocalDateTime;

import io.jungmini.sample.domain.post.model.entity.Post;
import io.jungmini.sample.domain.user.dto.UserResponse;

public record PostResponse(
    Long id,
    String title,
    String content,
    UserResponse user,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            UserResponse.from(post.getUser()),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}