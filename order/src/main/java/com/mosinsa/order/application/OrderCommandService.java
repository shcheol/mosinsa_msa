package com.mosinsa.order.application;

import com.mosinsa.order.application.dto.CreateOrderDto;
import com.mosinsa.order.application.dto.OrderDetailDto;
import com.mosinsa.order.application.dto.OrderDto;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.domain.*;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDetailDto order(CreateOrderDto createOrderDto, Predicate<CreateOrderDto> predicate) {

        Order order = orderRepository.save(
                Order.create(
                        createOrderDto.getCustomerId(),
                        createOrderDto.getMyOrderProducts().stream().map(
                                myOrderProduct -> OrderProduct.create(
                                        myOrderProduct.productId(),
                                        myOrderProduct.price(),
                                        myOrderProduct.quantity())
                        ).toList(),ShippingInfo.of(Address.of("","",""),"",Receiver.of("",""))));

        if (predicate.test(createOrderDto)) {
            log.info("order [{}] use coupon {}", order.getId().getId(), order.getCouponId());
            CouponResponse coupon = createOrderDto.getCouponResponse();
            order.useCoupon(coupon.couponId(), coupon.discountPolicy());
        }

        return new OrderDetailDto(order);
    }

    @Transactional
    public OrderDetailDto cancelOrder(String orderId) {
        Order findOrder = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));
        findOrder.cancelOrder();
        return new OrderDetailDto(findOrder);
    }

}
