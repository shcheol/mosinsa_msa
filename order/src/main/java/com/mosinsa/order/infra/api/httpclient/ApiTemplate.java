package com.mosinsa.order.infra.api.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.infra.api.ExternalDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Component
@RequiredArgsConstructor
public class ApiTemplate {

	private final ObjectMapper om;

	public <T> T getForObject(ExternalDomain domain, URI uri, Class<T> responseType) throws JsonProcessingException {



		T t = om.readValue("", responseType);
		return t;
	}

}
