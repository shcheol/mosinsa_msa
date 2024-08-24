package com.mosinsa.promotion.infra.api.feignclient;

import com.mosinsa.promotion.infra.api.HeaderConst;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestHeaderExtractorTest {

    @Test
    void extractEmpty(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockHttpServletRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        Map<String, Collection<String>> extract = RequestHeaderExtractor.extract();
        Assertions.assertThat(extract).isEmpty();
    }

    @Test
    void extract(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "bearer token");
        mockHttpServletRequest.addHeader(HeaderConst.REFRESH_TOKEN.key(), "refresh token");
        mockHttpServletRequest.addHeader(HeaderConst.CUSTOMER_INFO.key(), "customer info");
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockHttpServletRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        Map<String, Collection<String>> extract = RequestHeaderExtractor.extract();
        Assertions.assertThat(extract.get(HttpHeaders.AUTHORIZATION)).isEqualTo(List.of("bearer token"));
        Assertions.assertThat(extract.get(HeaderConst.REFRESH_TOKEN.key())).isEqualTo(List.of("refresh token"));
        Assertions.assertThat(extract.get(HeaderConst.CUSTOMER_INFO.key())).isEqualTo(List.of("customer info"));
    }

}