package com.mosinsa.order.query.application;

import com.mosinsa.order.command.application.NotEnoughProductStockException;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.command.domain.DiscountPolicy;
import com.mosinsa.order.infra.feignclient.coupon.CouponQueryService;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.infra.feignclient.customer.CustomerQueryService;
import com.mosinsa.order.infra.feignclient.product.ProductQueryService;
import com.mosinsa.order.infra.feignclient.product.ProductResponse;
import com.mosinsa.order.ui.argumentresolver.CustomerInfo;
import com.mosinsa.order.ui.request.MyOrderProduct;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConfirmTemplate {
	private final CustomerQueryService customerQueryService;
	private final CouponQueryService couponQueryService;
	private final ProductQueryService productQueryService;
	public OrderConfirmDto orderConfirm(Map<String, Collection<String>> authMap, CustomerInfo customerInfo, OrderConfirmRequest orderConfirmRequest) {

		// 유저 조회
		customerQueryService.customerCheck(authMap, customerInfo.id())
				.orElseThrow();

		// 상품 조회
		List<ProductResponse> productResponses = orderConfirmRequest
				.myOrderProducts().stream()
				.map(myOrderProduct -> productQueryService.productCheck(authMap, myOrderProduct).orElseThrow()).toList();

		Map<String, Integer> myOrderProductMap = orderConfirmRequest.myOrderProducts().stream()
				.collect(Collectors.toMap(MyOrderProduct::productId, MyOrderProduct::quantity));

		List<OrderProductDto> confirmOrderProducts = productResponses.stream()
				.map(productResponse -> {
					Integer orderStock = myOrderProductMap.getOrDefault(productResponse.productId(), Integer.MAX_VALUE);
					if (productResponse.stock() < orderStock) {
						log.info("product stock is not enough {}/{}", orderStock, productResponse.stock());
						throw new NotEnoughProductStockException("상품수량이 부족합니다.");
					}
					return OrderProductDto.builder()
							.price(productResponse.price())
							.productId(productResponse.productId())
							.quantity(orderStock)
							.amounts(productResponse.price() * orderStock)
							.build();
				}).toList();

		int sum = confirmOrderProducts.stream().mapToInt(OrderProductDto::amounts).sum();

		if (StringUtils.hasText(orderConfirmRequest.couponId())) {
			// 쿠폰 조회
			CouponResponse couponResponse = couponQueryService.couponCheck(authMap, orderConfirmRequest.couponId())
					.orElseThrow();
			// 토탈 금액
			sum -= DiscountPolicy.valueOf(couponResponse.discountPolicy()).applyDiscountPrice(sum);
		}

		return OrderConfirmDto.builder()
				.customerId(customerInfo.id())
				.couponId(orderConfirmRequest.couponId())
				.orderProducts(confirmOrderProducts)
				.totalAmount(sum)
				.shippingInfo(orderConfirmRequest.shippingInfo())
				.build();
	}
}
