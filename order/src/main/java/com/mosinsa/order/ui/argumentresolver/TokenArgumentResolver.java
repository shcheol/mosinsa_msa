package com.mosinsa.order.ui.argumentresolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.ui.HeaderConst;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthMapAnnotation = parameter.hasParameterAnnotation(AuthMap.class);
        boolean hasAuthTokenType = AuthToken.class.isAssignableFrom(parameter.getParameterType());
        return hasAuthMapAnnotation && hasAuthTokenType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

		Map<String, Collection<String>> authMap = new HashMap<>();
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(auth)) {
			authMap.put(HttpHeaders.AUTHORIZATION, List.of(auth));
		}
		String token = request.getHeader(HeaderConst.REFRESH_TOKEN.getName());
		if (StringUtils.hasText(token)) {
			authMap.put(HeaderConst.REFRESH_TOKEN.getName(), List.of(token));
		}
		return new AuthToken(authMap);

    }
}
