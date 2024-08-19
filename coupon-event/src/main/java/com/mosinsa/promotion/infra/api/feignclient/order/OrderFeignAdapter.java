package com.mosinsa.promotion.infra.api.feignclient.order;

import com.mosinsa.promotion.infra.api.HeaderConst;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderFeignAdapter implements OrderAdapter {

	private final OrderClient client;


	@Override
	public ResponseResult<List<OrderSummary>> getMyOrders(String customerId) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, Collection<String>> headers = new HashMap<>();
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(auth)) {
			headers.put(HttpHeaders.AUTHORIZATION, List.of(auth));
		}
		String token = request.getHeader(HeaderConst.REFRESH_TOKEN.key());
		if (StringUtils.hasText(token)) {
			headers.put(HeaderConst.REFRESH_TOKEN.key(), List.of(token));
		}
		String userInfo = request.getHeader(HeaderConst.CUSTOMER_INFO.key());
		if (StringUtils.hasText(userInfo)) {
			headers.put(HeaderConst.CUSTOMER_INFO.key(), List.of(userInfo));
		}
		return ResponseResult.execute(() -> client.myOrders(headers, customerId).getContent());

	}
}
