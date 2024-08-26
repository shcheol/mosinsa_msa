package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.common.ex.OrderRollbackException;
import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.kafka.KafkaEvents;
import com.mosinsa.order.infra.kafka.OrderCanceledEvent;
import com.mosinsa.order.query.application.dto.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
	private final CouponAdapter couponAdapter;
	private final ProductAdapter productAdaptor;
	private final PlaceOrderService placeOrderService;
	private final CancelOrderService cancelOrderService;

	@Value("${mosinsa.topic.order.cancel}")
	public String orderCancelTopic;


	@Override
	public OrderDetail order(OrderConfirmDto orderConfirmDto) {
		// 쿠폰 사용
		String couponId = orderConfirmDto.couponId();
		if (StringUtils.hasText(couponId)) {
			couponAdapter.useCoupon(couponId).orElseThrow();
		}

		// 상품 수량 감소
		OrderId orderId = OrderId.newId();
		productAdaptor.orderProducts(orderId.getId(), orderConfirmDto).orElseThrow();

		try {
			// 주문 db
			Order order = placeOrderService.order(orderId, orderConfirmDto);
			return new OrderDetail(order);
		} catch (Exception e) {
			log.error("order save fail => rollback product stock, coupon usage");

			KafkaEvents.raise(orderCancelTopic, new OrderCanceledEvent(
					"",
					orderConfirmDto.couponId(),
					orderConfirmDto.customerId(),
					orderConfirmDto.orderProducts()));
			throw new OrderRollbackException(e);
		}
	}

	@Override
	public OrderDetail cancelOrder(String orderId) {
		Order cancelOrder = cancelOrderService.cancelOrder(orderId);

		KafkaEvents.raise(orderCancelTopic,
				new OrderCanceledEvent(cancelOrder.getId().getId(),
						cancelOrder.getCustomerId(),
						cancelOrder.getOrderCoupon().getCouponId(),
						cancelOrder.getOrderProducts().stream().map(OrderProductDto::of).toList()));

		return new OrderDetail(cancelOrder);
	}
}
