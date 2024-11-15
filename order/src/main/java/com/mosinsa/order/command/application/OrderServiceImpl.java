package com.mosinsa.order.command.application;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.ex.OrderRollbackException;
import com.mosinsa.order.command.application.dto.CancelOrderInfo;
import com.mosinsa.order.command.application.dto.OrderInfo;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.httpinterface.product.OrderProductRequests;
import com.mosinsa.order.infra.kafka.OrderCanceledEvent;
import com.mosinsa.order.infra.kafka.ProduceTemplate;
import com.mosinsa.order.query.application.dto.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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

			OrderCanceledEvent ca = OrderCanceledEvent.builder()
					.id(orderId.getId())
					.customerInfo(orderInfo.customerInfo())
					.orderProducts(orderInfo.orderProducts().stream().map(op -> {
								List<OrderCanceledEvent.OrderProductInfo.ProductOptionsDto> productOptions = op.options()
										.stream()
										.map(option -> new OrderCanceledEvent.OrderProductInfo.ProductOptionsDto(option.id(), option.name()))
										.toList();
								List<OrderCanceledEvent.OrderProductInfo.CouponDto> coupons = op.coupons()
										.stream()
										.map(coupon -> new OrderCanceledEvent.OrderProductInfo.CouponDto(coupon.id(), coupon.discountPolicy()))
										.toList();
								return new OrderCanceledEvent.OrderProductInfo(op.id(), op.name(), op.quantity(), op.perPrice(), op.totalPrice(), productOptions, coupons);
							})
							.toList())
					.build();

			producerTemplate.produce(orderCancelTopic, ca);

			throw new OrderRollbackException(e);
		}
	}

	private void useCoupon(OrderInfo orderInfo) {
		orderInfo.orderProducts()
				.forEach(op -> op.coupons().forEach(c -> couponAdapter.useCoupon(c.id()).orElseThrow()));
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
	public OrderDetail cancelOrder(CancelOrderInfo cancelOrderInfo) {
		Order cancelOrder = cancelOrderService.cancelOrder(cancelOrderInfo.orderId());

		OrderCanceledEvent event = OrderCanceledEvent.builder()
				.id(cancelOrderInfo.orderId())
				.customerInfo(cancelOrderInfo.customerInfo())
				.orderProducts(cancelOrder.getOrderProducts().stream().map(op -> {
							List<OrderCanceledEvent.OrderProductInfo.ProductOptionsDto> productOptions = op.getProductOptions()
									.stream()
									.map(option -> new OrderCanceledEvent.OrderProductInfo.ProductOptionsDto(option.getOptionId(), option.getName()))
									.toList();
							List<OrderCanceledEvent.OrderProductInfo.CouponDto> coupons = op.getOrderCoupons()
									.stream()
									.map(coupon -> new OrderCanceledEvent.OrderProductInfo.CouponDto(coupon.getCouponId(), coupon.getDiscountPolicy()))
									.toList();
							return new OrderCanceledEvent.OrderProductInfo(op.getProductId(), op.getName(), op.getQuantity(), op.getPrice().getValue(), op.getAmounts().getValue(), productOptions, coupons);
						})
						.toList())
				.build();

				producerTemplate.produce(orderCancelTopic, event);
		return OrderDetail.of(cancelOrder);
	}
}
