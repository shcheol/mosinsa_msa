package com.mosinsa.order.query.application;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.api.feignclient.coupon.CouponResponse;
import com.mosinsa.order.ui.argumentresolver.CustomerInfo;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConfirmTemplate {

    private final CouponAdapter couponAdapter;
    private final ProductAdapter productAdaptor;

    public OrderConfirmDto orderConfirm(Map<String, Collection<String>> authMap, CustomerInfo customerInfo,
                                        OrderConfirmRequest orderConfirmRequest) {

        List<OrderProductDto> confirmOrderProducts = productAdaptor.confirm(authMap, orderConfirmRequest);
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
        CouponResponse coupon = couponAdapter.getCoupon(authMap, couponId)
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
