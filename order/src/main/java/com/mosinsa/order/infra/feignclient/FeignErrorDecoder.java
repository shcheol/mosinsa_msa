package com.mosinsa.order.infra.feignclient;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
		log.error("=================================");
        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                if (methodKey.contains("getCoupon")) {
                    return new CouponNotFoundException(response.reason());
                }
                break;
            case 500:
            case 503:
                return new CouponServiceNotAvailableException(response.reason());
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
