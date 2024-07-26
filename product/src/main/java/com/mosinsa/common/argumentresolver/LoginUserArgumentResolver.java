package com.mosinsa.common.argumentresolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.security.auth.login.LoginException;
import java.util.Optional;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final ObjectMapper om;

	public LoginUserArgumentResolver(ObjectMapper om) {
		this.om = om;
	}

	@Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasCustomerInfoType = CustomerInfo.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasCustomerInfoType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String customerInfoJson = Optional.ofNullable(request.getHeader("customer-info"))
				.orElseThrow(() -> new ProductException(ProductError.UNAUTHORIZED_ERROR));

        String customerInfoJsonForJava = StringEscapeUtils.unescapeJava(customerInfoJson.substring(1, customerInfoJson.length() - 1));

        return om.readValue(customerInfoJsonForJava, CustomerInfo.class);
    }
}
