package com.mosinsa.promotion.infra.api.httpinterface;

import com.mosinsa.promotion.infra.api.ExternalServerException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ApiErrorHandler extends DefaultResponseErrorHandler {
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		throw new ExternalServerException(response.getStatusCode(),
				StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
	}
}
