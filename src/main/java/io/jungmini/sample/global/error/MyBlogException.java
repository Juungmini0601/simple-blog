package io.jungmini.sample.global.error;

import lombok.Getter;

@Getter
public class MyBlogException extends RuntimeException {
	private final ErrorType errorType;
	private final Object data;

	public MyBlogException(ErrorType errorType) {
		super(errorType.getMessage());
		this.errorType = errorType;
		this.data = null;
	}

	public MyBlogException(ErrorType errorType, Object data) {
		super(errorType.getMessage());
		this.errorType = errorType;
		this.data = data;
	}
}
