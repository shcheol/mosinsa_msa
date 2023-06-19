package com.mosinsa.customer.feignclient;

import com.mosinsa.customer.web.controller.response.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "order-service", url = "localhost:8000")
public interface OrderServiceClient {

    @GetMapping("/order-service/{customerId}/orders")
    List<ResponseOrder> getOrders(@PathVariable(value = "customerId") Long customerId);
}
