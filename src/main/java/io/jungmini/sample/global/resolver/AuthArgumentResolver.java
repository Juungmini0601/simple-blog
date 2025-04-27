package io.jungmini.sample.global.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.jungmini.sample.domain.user.model.entity.User;
import io.jungmini.sample.global.annotation.Auth;
import io.jungmini.sample.global.error.ErrorType;
import io.jungmini.sample.global.error.MyBlogException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Auth.class) && parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = httpRequest.getSession(false);

		if (session == null) {
			throw new MyBlogException(ErrorType.AUTHENTICATION_ERROR);
		}
		// TODO 상수화 필요
		return session.getAttribute("LOGIN_USER");
	}
}