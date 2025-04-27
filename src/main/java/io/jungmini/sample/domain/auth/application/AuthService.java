package io.jungmini.sample.domain.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jungmini.sample.domain.auth.dao.OauthRepository;
import io.jungmini.sample.domain.auth.model.OAuthProvider;
import io.jungmini.sample.domain.auth.model.entity.Oauth;
import io.jungmini.sample.domain.user.dao.UserRepository;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.error.ErrorType;
import io.jungmini.sample.global.error.MyBlogException;
import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final OauthRepository oauthRepository;

	/**
	 * 일반 로그인
	 * @param email 이메일
	 * @param password 비밀번호
	 * @return User 유저
	 */
	public User login(String email, String password) {
		User user = userRepository.findByEmailOrElseThrow(email);

		// 실제로는 암호화 클래스의 match
		if (!user.getPassword().equals(password)) {
			throw new MyBlogException(ErrorType.AUTHENTICATION_ERROR, "이메일 또는 비밀번호가 일치하지 않습니다.");
		}

		return user;
	}

	/**
	 *
	 * @param email 이메일
	 * @param providerId oauth provider id
	 * @param oAuthProvider oauth provider
	 * @return
	 */
	@Transactional
	public User oauth2Login(String email, String providerId, OAuthProvider oAuthProvider) {
		// 소셜 로그인 정보가 있을 경우 유저 정보를 찾아서 반환
		return oauthRepository.findByProviderIdAndProvider(providerId, oAuthProvider)
			.orElseGet(() -> {
				// 소셜 로그인 정보가 없을 경우 일반 회원 유저인지 확인
				User user = userRepository.findByEmail(email).orElse(null);

				// 일반 회원이었으면 소셜 연동 정보 추가
				if (user != null) {
					Oauth oauth = new Oauth(oAuthProvider, providerId, user.getId());
					oauthRepository.save(oauth);
					return user;
				}

				// 일반 회원도 아니었으면 회원을 생성
				User newUser = new User(email, null);
				User savedUser = userRepository.save(newUser);

				Oauth oauth = new Oauth(oAuthProvider, providerId, savedUser.getId());
				oauthRepository.save(oauth);

				return newUser;
			});
	}
}
