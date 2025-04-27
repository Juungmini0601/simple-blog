package io.jungmini.sample.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(new Info()
				.title("블로그 API")
				.description("Simple Blog API 문서")
				.version("v1.0.0"));
	}
}
