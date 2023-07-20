package com.mosinsa.order.feignclient;

import com.mosinsa.order.controller.request.RequestCreateCustomer;
import com.mosinsa.order.controller.response.ResponseCustomer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/customers/{customerId}")
    ResponseCustomer getCustomer(@PathVariable(value = "customerId") Long customerId);

    @PostMapping("/customer")
    ResponseCustomer createCustomer(@RequestBody RequestCreateCustomer request);
}
