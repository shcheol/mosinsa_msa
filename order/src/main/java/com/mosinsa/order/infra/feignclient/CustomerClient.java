package com.mosinsa.order.infra.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "customer-service", url = "${feignclient.url.customer}")
public interface CustomerClient {

    @GetMapping("/customers/{customerId}")
	CustomerResponse getCustomer(@RequestHeader Map<String, Collection<String>> headers, @PathVariable(value = "customerId") String customerId);
}
