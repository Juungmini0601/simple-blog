package io.jungmini.sample.global.error;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jungmini.sample.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = RestController.class, basePackages = "io.jungmini.sample.domain")
public class ApiControllerAdvice {

	@ExceptionHandler(MyBlogException.class)
	public ResponseEntity<ApiResponse<?>> handleJavaKataException(MyBlogException exception) {
		switch (exception.getErrorType().getLogLevel()) {
			case ERROR -> log.error("JavaKataException : {}", exception.getMessage(), exception);
			case WARN -> log.warn("JavaKataException : {}", exception.getMessage(), exception);
			default -> log.info("JavaKataException : {}", exception.getMessage(), exception);
		}

		return new ResponseEntity<>(
			ApiResponse.error(exception.getErrorType(), exception.getData()), exception.getErrorType().getStatus()
		);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {

		Map<String, String> validationErrors = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				DefaultMessageSourceResolvable::getDefaultMessage,
				(existing, replacement) -> existing // 중복 키 발생 시 기존 값을 유지
			));

		return ResponseEntity
			.status(ErrorType.VALIDATION_ERROR.getStatus())
			.body(ApiResponse.error(ErrorType.VALIDATION_ERROR, validationErrors));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
		log.error("Exception : {}", exception.getMessage(), exception);
		return new ResponseEntity<>(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.getStatus());
	}
}