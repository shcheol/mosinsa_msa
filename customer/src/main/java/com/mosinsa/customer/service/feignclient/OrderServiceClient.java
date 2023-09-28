package com.mosinsa.customer.service.feignclient;

import com.mosinsa.customer.web.controller.response.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "order-service", url = "${feignclient.url.order}")
public interface OrderServiceClient {

    @GetMapping("/{customerId}/orders")
    List<ResponseOrder> getOrders(@PathVariable(value = "customerId") String customerId);
}
