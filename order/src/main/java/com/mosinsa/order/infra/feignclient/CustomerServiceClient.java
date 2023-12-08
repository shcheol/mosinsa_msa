package com.mosinsa.order.infra.feignclient;

import com.mosinsa.order.ui.request.RequestCreateCustomer;
import com.mosinsa.order.ui.response.ResponseCustomer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "customer-service", url = "${feignclient.url.customer}")
public interface CustomerServiceClient {

    @GetMapping("/customers/{customerId}")
	ResponseCustomer getCustomer(@RequestHeader Map<String, Collection<String>> headers, @PathVariable(value = "customerId") String customerId);

    @PostMapping("/customers")
	String createCustomer(@RequestBody RequestCreateCustomer request);

	@DeleteMapping("/customers/{customerId}")
	void deleteCustomer(@PathVariable(value = "customerId") String customerId);
}
