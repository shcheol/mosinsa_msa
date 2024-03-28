package com.mosinsa.order.infra.feignclient;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
		log.error("call {} fail", methodKey);
        return new ResponseFailureException(response);
    }
}
