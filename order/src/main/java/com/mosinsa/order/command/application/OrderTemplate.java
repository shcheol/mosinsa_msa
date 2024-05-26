package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.command.domain.DiscountPolicy;
import com.mosinsa.order.common.ex.OrderRollbackException;
import com.mosinsa.order.infra.feignclient.coupon.CouponCommandService;
import com.mosinsa.order.infra.feignclient.coupon.CouponQueryService;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.infra.feignclient.customer.CustomerQueryService;
import com.mosinsa.order.infra.feignclient.product.ProductCommandService;
import com.mosinsa.order.infra.feignclient.product.ProductQueryService;
import com.mosinsa.order.infra.feignclient.product.ProductResponse;
import com.mosinsa.order.infra.kafka.CouponCanceledEvent;
import com.mosinsa.order.infra.kafka.KafkaEvents;
import com.mosinsa.order.infra.kafka.OrderProductCanceledEvent;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.MyOrderProduct;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderTemplate {

    private final CustomerQueryService customerQueryService;
    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;
    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;
    private final PlaceOrderService placeOrderService;
    private final CancelOrderService cancelOrderService;

    public OrderConfirmDto orderConfirm(Map<String, Collection<String>> authMap, OrderConfirmRequest orderConfirmRequest) {

        // 유저 조회
        customerQueryService.customerCheck(authMap, orderConfirmRequest.customerId())
                .orElseThrow();

        // 상품 조회
        List<ProductResponse> productResponses = orderConfirmRequest
                .myOrderProducts().stream()
                .map(myOrderProduct -> productQueryService.productCheck(authMap, myOrderProduct).orElseThrow()).toList();

        List<MyOrderProduct> myOrderProducts = orderConfirmRequest.myOrderProducts();
        List<OrderProductDto> confirmOrderProducts = new ArrayList<>();
        for (int i = 0; i < myOrderProducts.size(); i++) {
            ProductResponse productResponse = productResponses.get(i);
            MyOrderProduct myOrderProduct = myOrderProducts.get(i);
            log.info("productResponse {}", productResponse.toString());
            if (myOrderProduct.quantity() > productResponse.stock()) {
                log.info("order quantity {}, product stock {}", myOrderProduct.quantity(), productResponse.stock());
                throw new NotEnoughProductStockException();
            }
            confirmOrderProducts.add(OrderProductDto.builder()
                    .price(productResponse.price())
                    .productId(productResponse.productId())
                    .quantity(myOrderProduct.quantity())
                    .amounts(productResponse.price() * myOrderProduct.quantity())
                    .build());
        }

        int sum = confirmOrderProducts.stream().mapToInt(OrderProductDto::amounts).sum();

        if (StringUtils.hasText(orderConfirmRequest.couponId())) {
            // 쿠폰 조회
            CouponResponse couponResponse = couponQueryService.couponCheck(authMap, orderConfirmRequest.couponId())
                    .orElseThrow();
            // 토탈 금액
            sum -= DiscountPolicy.valueOf(couponResponse.discountPolicy()).applyDiscountPrice(sum);
        }

        return OrderConfirmDto.builder()
                .customerId(orderConfirmRequest.customerId())
                .couponId(orderConfirmRequest.couponId())
                .orderProducts(confirmOrderProducts)
                .totalAmount(sum)
                .shippingInfo(orderConfirmRequest.shippingInfo())
                .build();
    }

    public OrderDetail order(Map<String, Collection<String>> authMap, CreateOrderRequest orderRequest) {

        // 쿠폰 사용
        if (StringUtils.hasText(orderRequest.orderConfirm().couponId())) {
            couponCommandService.useCoupon(authMap, orderRequest.orderConfirm().couponId()).orElseThrow();
        }
        // 상품 수량 감소
        productCommandService.orderProduct(authMap, orderRequest).orElseThrow();

        try {
            // 주문 db
            return placeOrderService.order(orderRequest);
        } catch (Exception e) {
            log.error("order save fail => rollback product stock, coupon use");

            productCommandService.cancelOrderProduct(authMap, orderRequest.orderConfirm().orderProducts())
                    .onFailure(() -> KafkaEvents.raise(new OrderProductCanceledEvent()));
            couponCommandService.cancelCoupon(authMap, orderRequest.orderConfirm().couponId())
                    .onFailure(() -> KafkaEvents.raise(new CouponCanceledEvent()));

            throw new OrderRollbackException(e);
        }

    }

    public OrderDetail cancelOrder(Map<String, Collection<String>> authMap, String orderId) {

        OrderDetail cancelOrder = cancelOrderService.cancelOrder(orderId);

        productCommandService.cancelOrderProduct(authMap, cancelOrder.getOrderProducts())
                .onFailure(() -> KafkaEvents.raise(new OrderProductCanceledEvent()));
        couponCommandService.cancelCoupon(authMap, cancelOrder.getCouponId())
                .onFailure(() -> KafkaEvents.raise(new CouponCanceledEvent()));
        return cancelOrder;
    }
}
