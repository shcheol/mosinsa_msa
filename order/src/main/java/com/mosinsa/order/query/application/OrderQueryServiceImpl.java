package com.mosinsa.order.query.application;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.common.argumentresolver.CustomerInfo;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.command.domain.OrderRepository;
import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.feignclient.coupon.CouponResponse;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {
	private final OrderRepository orderRepository;
	private final CouponAdapter couponAdapter;
	private final ProductAdapter productAdaptor;

	@Override
	public Page<OrderSummary> findMyOrdersByCondition(SearchCondition condition, Pageable pageable) {
		if (!StringUtils.hasText(condition.customerId())) {
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		return orderRepository.findOrdersByCondition(condition, pageable).map(OrderSummary::new);
	}

	@Override
	public OrderDetail getOrderDetails(String orderId) {
		return new OrderDetail(orderRepository.findById(OrderId.of(orderId))
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND)));
	}


	@Override
	public OrderConfirmDto orderConfirm(CustomerInfo customerInfo, OrderConfirmRequest orderConfirmRequest) {

		List<OrderProductDto> confirmOrderProducts = productAdaptor.confirm(orderConfirmRequest);
		int sum = calculateTotalAmount(confirmOrderProducts);
		OrderConfirmDto confirmDto = OrderConfirmDto.builder()
				.customerId(customerInfo.id())
				.orderProducts(confirmOrderProducts)
				.totalAmount(sum)
				.shippingInfo(orderConfirmRequest.shippingInfo())
				.build();

		if (!isOrderWithCoupon(orderConfirmRequest)) {
			return confirmDto;
		}

		String couponId = orderConfirmRequest.couponId();
		CouponResponse coupon = couponAdapter.getCoupon(couponId)
				.orElseThrow();

		return confirmDto.useCoupon(couponId, coupon.discountPolicy());
	}

	private boolean isOrderWithCoupon(OrderConfirmRequest orderConfirmRequest) {
		return StringUtils.hasText(orderConfirmRequest.couponId());
	}

	private int calculateTotalAmount(List<OrderProductDto> confirmOrderProducts) {
		return confirmOrderProducts.stream().mapToInt(OrderProductDto::amounts).sum();
	}
}
