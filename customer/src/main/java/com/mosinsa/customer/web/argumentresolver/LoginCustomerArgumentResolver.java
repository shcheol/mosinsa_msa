package com.mosinsa.customer.web.argumentresolver;

import com.mosinsa.customer.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginCustomerArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter");
        //@Login이 붙어있는지
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);

        //타입이 맞는지
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasParameterAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // supportParameter true일때 실행

        log.info("resolveArgument");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if(session == null){
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_CUSTOMER);
    }
}
