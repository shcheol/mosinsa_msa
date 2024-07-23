package com.mosinsa.common.argumentresolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class GuestOrLoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasGuestOrLoginAnnotation = parameter.hasParameterAnnotation(GuestOrLogin.class);
        boolean hasCustomerInfoType = CustomerInfo.class.isAssignableFrom(parameter.getParameterType());
        return hasGuestOrLoginAnnotation && hasCustomerInfoType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String customerInfoJson = request.getHeader("customer-info");
        String customerInfoJsonForJava = StringEscapeUtils.unescapeJava(customerInfoJson.substring(1, customerInfoJson.length() - 1));
        try {
            return new ObjectMapper().readValue(customerInfoJsonForJava, CustomerInfo.class);
        }catch (Exception e){
            return new CustomerInfo("","guest");

        }
    }
}
