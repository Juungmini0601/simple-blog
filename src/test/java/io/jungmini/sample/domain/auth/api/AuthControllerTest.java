package io.jungmini.sample.domain.auth.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import io.jungmini.sample.AbstractIntegrationTest;
import io.jungmini.sample.domain.auth.dto.LoginRequest;
import io.jungmini.sample.domain.user.dao.UserRepository;
import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.error.ErrorCode;

@DisplayName("AuthController")
public class AuthControllerTest extends AbstractIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Nested
	@DisplayName("로그인 API")
	class Login {

		private final String TEST_EMAIL = "test@example.com";
		private final String TEST_PASSWORD = "password123";

		@BeforeEach
		void setup() {
			userRepository.save(new User(TEST_EMAIL, TEST_PASSWORD));
		}

		@Test
		@DisplayName("성공")
		public void success() throws Exception {
			// Given
			LoginRequest request = new LoginRequest(TEST_EMAIL, TEST_PASSWORD);

			// When
			ResultActions result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

			// Then
			result.andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value("SUCCESS"))
				.andDo(print());
		}

		@Test
		@DisplayName("이메일이 존재하지 않으면 실패")
		public void when_email_not_exists_then_fail() throws Exception {
			// Given
			LoginRequest request = new LoginRequest("nonexistent@example.com", TEST_PASSWORD);

			// When
			ResultActions result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

			// Then
			result.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error.code").value(ErrorCode.E401.toString()))
				.andDo(print());
		}

		@Test
		@DisplayName("비밀번호가 일치하지 않으면 실패")
		public void when_password_incorrect_then_fail() throws Exception {
			// Given
			LoginRequest request = new LoginRequest(TEST_EMAIL, "wrongpassword");

			// When
			ResultActions result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

			// Then
			result.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error.code").value(ErrorCode.E401.toString()))
				.andDo(print());
		}

		@Test
		@DisplayName("유효하지 않은 입력 값이면 실패")
		public void when_input_invalid_then_fail() throws Exception {
			// Given
			LoginRequest request = new LoginRequest("", "");

			// When
			ResultActions result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

			// Then
			result.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.code").value(ErrorCode.E400.toString()))
				.andDo(print());
		}
	}
}