package io.jungmini.sample.domain.user.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import io.jungmini.sample.AbstractIntegrationTest;
import io.jungmini.sample.domain.user.dto.UserRegistrationRequest;

@DisplayName("UserController")
public class UserControllerTest extends AbstractIntegrationTest {

	@Nested
	@DisplayName("회원 가입 API")
	class RegisterUser {
		@Test
		@DisplayName("성공")
		public void success() throws Exception {
			// Given
			UserRegistrationRequest request = new UserRegistrationRequest(
				"test@example.com",
				"password123"
			);

			// When
			ResultActions result = mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

			// Then
			result.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.email").value("test@example.com"));
		}

		@Test
		@DisplayName("이메일이 유효하지 않으면 실패")
		public void when_email_invalid_then_fail() throws Exception {
			// Given
			UserRegistrationRequest request = new UserRegistrationRequest(
				"invalid-email",
				"password123"
			);

			// When
			ResultActions result = mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

			// Then
			result.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.data.email").exists())
				.andDo(print());
		}

		@Test
		@DisplayName("유효하지 않은 입력 값이면 실패")
		public void when_input_invalid_then_fail() throws Exception {
			// Given
			UserRegistrationRequest request = new UserRegistrationRequest(
				"",
				""
			);

			// When
			ResultActions result = mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

			// Then
			result.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.data.email").exists())
				.andDo(print());
		}
	}
}