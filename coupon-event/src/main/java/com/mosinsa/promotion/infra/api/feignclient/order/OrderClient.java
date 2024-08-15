package com.mosinsa.promotion.infra.api.feignclient.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "order-service", url = "${feignclient.url.order}")
public interface OrderClient {

    @GetMapping("/orders")
    void getCoupon(@RequestHeader Map<String, Collection<String>> headers
                   );

}
