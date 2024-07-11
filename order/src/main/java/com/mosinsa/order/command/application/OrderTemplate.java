package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.common.ex.OrderRollbackException;
import com.mosinsa.order.infra.api.feignclient.coupon.CouponCommandService;
import com.mosinsa.order.infra.api.feignclient.product.ProductCommandService;
import com.mosinsa.order.infra.kafka.KafkaEvents;
import com.mosinsa.order.infra.kafka.OrderCanceledEvent;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderTemplate {

	private final CouponCommandService couponCommandService;
	private final ProductCommandService productCommandService;
	private final PlaceOrderService placeOrderService;

	@Value("${mosinsa.topic.order.cancel}")
	public String orderCancelTopic;


	public OrderDetail order(Map<String, Collection<String>> authMap, CreateOrderRequest orderRequest) {

		// 쿠폰 사용
		String couponId = orderRequest.orderConfirm().couponId();
		if (StringUtils.hasText(couponId)) {
			couponCommandService.useCoupon(authMap, orderRequest.orderConfirm().couponId()).orElseThrow();
		}

		// 상품 수량 감소
		OrderId orderId = OrderId.newId();
		productCommandService.orderProduct(authMap, orderId.getId(), orderRequest).orElseThrow();

		try {
			// 주문 db
			return placeOrderService.order(orderId, orderRequest);
		} catch (Exception e) {
			log.error("order save fail => rollback product stock, coupon usage");
			OrderConfirmDto orderConfirmDto = orderRequest.orderConfirm();

			KafkaEvents.raise(orderCancelTopic, new OrderCanceledEvent(
					"",
					orderConfirmDto.couponId(),
					orderConfirmDto.customerId(),
					orderConfirmDto.orderProducts()));
			throw new OrderRollbackException(e);
		}

	}

}
