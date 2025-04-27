package io.jungmini.sample.domain.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.error.ErrorType;
import io.jungmini.sample.global.error.MyBlogException;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	default User findByEmailOrElseThrow(String email) {
		return findByEmail(email)
			.orElseThrow(() -> new MyBlogException(ErrorType.AUTHENTICATION_ERROR, "이메일 또는 비밀번호가 일치하지 않습니다."));
	}

	default void existsByEmailDoThrow(String email) {
		if (existsByEmail(email)) {
			throw new MyBlogException(ErrorType.CONFLICT_ERROR, "중복된 이메일 입니다.");
		}
	}
}
