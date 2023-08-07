package com.mosinsa.order.service.feignclient;

import com.mosinsa.order.controller.request.RequestCreateCustomer;
import com.mosinsa.order.controller.response.ResponseCustomer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "customer-service", url = "localhost:8000/customer-service")
public interface CustomerServiceClient {

    @GetMapping("/customer/{customerId}")
    ResponseCustomer getCustomer(@PathVariable(value = "customerId") Long customerId);

    @PostMapping("/customer")
    Long createCustomer(@RequestBody RequestCreateCustomer request);

	@DeleteMapping("/customer/{customerId}")
	void deleteCustomer(@PathVariable(value = "customerId") Long customerId);
}
