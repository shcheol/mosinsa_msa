package com.mosinsa.order.command.application;

import com.mosinsa.common.ex.OrderRollbackException;
import com.mosinsa.order.command.application.dto.OrderInfo;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.httpinterface.product.OrderProductRequests;
import com.mosinsa.order.infra.kafka.ProduceTemplate;
import com.mosinsa.order.query.application.dto.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final CouponAdapter couponAdapter;
	private final ProductAdapter productAdaptor;
	private final PlaceOrderService placeOrderService;
	private final CancelOrderService cancelOrderService;
	private final ProduceTemplate producerTemplate;

	@Value("${mosinsa.topic.order.cancel}")
	public String orderCancelTopic;


	@Override
	public OrderDetail order(OrderInfo orderInfo) {

		// 상품 수량 감소
		OrderId orderId = decreaseStock(orderInfo);
		useCoupon(orderInfo);

		try {
			// 주문 db
			Order order = placeOrderService.order(orderId, orderInfo);
			return OrderDetail.of(order);
		} catch (Exception e) {
			log.error("order save fail => rollback product stock, coupon usage");
//			OrderCanceledEvent event = new OrderCanceledEvent(
//					orderId.getId(),
//					orderRequest.couponId(),
//					orderRequest.customerId(),
//					orderRequest.orderProducts());
//			producerTemplate.produce(orderCancelTopic, event);
			throw new OrderRollbackException(e);
		}
	}

	private void useCoupon(OrderInfo orderInfo) {
		orderInfo.orderProducts()
				.forEach(op -> op.coupons().forEach(c -> couponAdapter.useCoupon(c.id())));
	}

	private OrderId decreaseStock(OrderInfo orderInfo) {
		OrderId orderId = OrderId.newId();
		OrderProductRequests orderProductRequests = new OrderProductRequests(orderId.getId(),
				orderInfo.orderProducts()
						.stream()
						.map(op -> new OrderProductRequests.OrderProductDto(op.id(), op.quantity(),
								op.options()
										.stream()
										.map(option -> new OrderProductRequests.OrderProductDto
												.ProductOptionsDto(option.id(), option.name()))
										.toList()))
						.toList());
		productAdaptor.orderProducts(orderId.getId(), orderProductRequests).orElseThrow();
		return orderId;
	}

	@Override
	public OrderDetail cancelOrder(String orderId) {
		Order cancelOrder = cancelOrderService.cancelOrder(orderId);

//		OrderCanceledEvent event = new OrderCanceledEvent(
//				cancelOrder.getId().getId(),
//				cancelOrder.getCustomerId(),
//				cancelOrder.getOrderCoupon().getCouponId(),
//				cancelOrder.getOrderProducts().stream().map(OrderProductDto::of).toList());
//		producerTemplate.produce(orderCancelTopic, event);
		return OrderDetail.of(cancelOrder);
	}
}
