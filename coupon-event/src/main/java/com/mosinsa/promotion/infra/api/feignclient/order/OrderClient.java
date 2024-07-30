package com.mosinsa.promotion.infra.api.feignclient.order;

import com.mosinsa.promotion.infra.api.CustomPageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

@FeignClient(name = "order-service", url = "${feignclient.url.order}")
public interface OrderClient {

	@GetMapping("/orders")
	CustomPageImpl<OrderSummary> myOrders(@RequestHeader Map<String, Collection<String>> headers, @RequestParam("customerId")String customerId);

}
