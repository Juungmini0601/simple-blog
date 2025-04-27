package io.jungmini.sample.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하로 입력해주세요.")
    String title,
    
    @NotBlank(message = "내용은 필수 입력값입니다.")
    @Size(min = 1, max = 5000, message = "내용은 1자 이상 5000자 이하로 입력해주세요.")
    String content
) {}