package io.jungmini.sample.domain.post.model.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 27. 
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
	private Long id;
	private String title;
	private String content;
	private Long userId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public boolean isOwner(Long userId) {
		return userId.equals(this.userId);
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
