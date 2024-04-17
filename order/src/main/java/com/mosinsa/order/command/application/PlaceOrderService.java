package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.CreateOrderDto;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.command.domain.*;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.infra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDetail order(CreateOrderDto createOrderDto, Predicate<CreateOrderDto> predicate) {

        Order order = orderRepository.save(
                Order.create(
                        createOrderDto.getCustomerId(),
                        createOrderDto.getMyOrderProducts().stream().map(
                                myOrderProduct -> OrderProduct.create(
                                        myOrderProduct.productId(),
                                        myOrderProduct.price(),
                                        myOrderProduct.quantity())
                        ).toList(), ShippingInfo.of(createOrderDto.getShippingInfo())));

        if (predicate.test(createOrderDto)) {
            log.info("order [{}] use coupon {}", order.getId().getId(), order.getCouponId());
            CouponResponse coupon = createOrderDto.getCouponResponse();
            order.useCoupon(coupon.couponId(), coupon.discountPolicy());
        }

        return new OrderDetail(order);
    }

}
