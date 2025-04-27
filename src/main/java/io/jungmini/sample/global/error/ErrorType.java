package io.jungmini.sample.global.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "validation error has occurred", LogLevel.DEBUG),
	NOT_SUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, ErrorCode.E400, "not support provider", LogLevel.DEBUG),
	AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, ErrorCode.E401, "authentication error has occurred", LogLevel.DEBUG),
	TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, ErrorCode.E401, "token expired error has occurred", LogLevel.DEBUG),
	AUTHORIZATION_ERROR(HttpStatus.FORBIDDEN, ErrorCode.E403, "authorization error has occurred", LogLevel.DEBUG),
	CONFLICT_ERROR(HttpStatus.CONFLICT, ErrorCode.E409, "conflict error has occurred", LogLevel.DEBUG),

	DEFAULT_ERROR(
		HttpStatus.INTERNAL_SERVER_ERROR,
		ErrorCode.E500,
		"An unexpected error has occurred.",
		LogLevel.ERROR);

	private final HttpStatus status;
	private final ErrorCode code;
	private final String message;
	private final LogLevel logLevel;
}