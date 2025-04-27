package io.jungmini.sample.domain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jungmini.sample.domain.user.dao.UserRepository;
import io.jungmini.sample.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	/**
	 * 유저 생성, 별도의 암호화 진행 X
	 * @param email 이메일
	 * @param password 비밀번호
	 * @return User
	 */
	@Transactional
	public User register(String email, String password) {
		userRepository.existsByEmailDoThrow(email);

		// ... 비밀번호 암호화 로직

		return userRepository.save(new User(email, password));
	}
}