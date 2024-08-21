package com.mosinsa.promotion.infra.api.feignclient;

import com.mosinsa.promotion.infra.api.HeaderConst;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;
import java.util.Map;

class RequestHeaderExtractorTest {

    @Test
    void extractEmpty() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockHttpServletRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        Map<String, Collection<String>> extract = RequestHeaderExtractor.extract();
        Assertions.assertThat(extract).isEmpty();
    }

    @Test
    void extract() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "bearer token");
        mockHttpServletRequest.addHeader(HeaderConst.REFRESH_TOKEN.key(), "refresh token");
        mockHttpServletRequest.addHeader(HeaderConst.CUSTOMER_INFO.key(), "customer info");
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockHttpServletRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        Map<String, Collection<String>> extract = RequestHeaderExtractor.extract();

        Assertions.assertThat(extract).containsEntry(HttpHeaders.AUTHORIZATION, List.of("bearer token"))
                .containsEntry(HeaderConst.REFRESH_TOKEN.key(), List.of("refresh token"))
                .containsEntry(HeaderConst.CUSTOMER_INFO.key(), List.of("customer info"));
    }

}