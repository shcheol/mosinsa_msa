package com.mosinsa.order.infra.feignclient.customer;

import com.mosinsa.order.infra.feignclient.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerQueryService {

	private final CustomerClient customerClient;

	public ResponseResult<CustomerResponse> customerCheck(Map<String, Collection<String>> headers, String customerId) {

		if (!StringUtils.hasText(customerId)){
			throw new IllegalArgumentException();
		}

		return ResponseResult.execute(() -> customerClient.getCustomer(headers, customerId));
	}

}
