package com.mosinsa.order.service;


import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.controller.request.SearchCondition;
import com.mosinsa.order.controller.response.ResponseCustomer;
import com.mosinsa.order.db.dto.OrderDto;
import com.mosinsa.order.db.dto.OrderProductDto;
import com.mosinsa.order.db.dto.ProductDto;
import com.mosinsa.order.db.entity.Order;
import com.mosinsa.order.db.entity.OrderProduct;
import com.mosinsa.order.db.entity.OrderStatus;
import com.mosinsa.order.db.repository.OrderRepository;
import com.mosinsa.order.service.feignclient.CustomerServiceClient;
import com.mosinsa.order.service.feignclient.ProductServiceClient;
import com.mosinsa.order.service.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

	private final CustomerServiceClient customerServiceClient;
    private final KafkaProducer kafkaProducer;

	@Value("${mosinsa.topic.order.request}")
	private String orderTopic;

	@Value("${mosinsa.topic.order.cancel}")
	private String cancelTopic;


	@Override
    @Transactional
    public OrderDto order(Long customerId, Map<String, Integer> productMap) {
        Assert.isTrue(productMap.size()>=1, "1개 이상의 상품을 주문해야 합니다.");

		ResponseCustomer customer =customerServiceClient.getCustomer(customerId);

		List<OrderProduct> orderProductList = new ArrayList<>();
        List<OrderProductDto> orderProductDtos = new ArrayList<>();

		productMap.keySet().forEach(productId ->{
			orderProductList.add(OrderProduct.createOrderProduct(productId, productMap.get(productId)));
			orderProductDtos.add(
					new OrderProductDto(
							OrderProduct.createOrderProduct(productId, productMap.get(productId)).getId(),
							productMap.get(productId), new ProductDto(productServiceClient.getProduct(productId))));
		});

		Order order = orderRepository.save(Order.createOrder(customer.getId(), orderProductList));
		OrderDto orderDto = new OrderDto(order.getId(), customer.getId(), getTotalPrice(order.getId()), OrderStatus.CREATE, orderProductDtos);
		kafkaProducer.send(orderTopic, orderDto);

		return orderDto;
    }

	@Override
    @Transactional
    public void cancelOrder(Long customerId, Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));
		Assert.isTrue(Objects.equals(findOrder.getCustomerId(), customerId), "주문한 고객과 동일한 고객이 취소해야합니다.");

		OrderStatus status = findOrder.getStatus();
        Assert.isTrue(!status.equals(OrderStatus.CANCEL),"이미 취소처리된 주문입니다.");

        Long orderCustomer = findOrder.getCustomerId();
        Assert.isTrue(orderCustomer.equals(customerId),"본인이 주문한 주문만 취소 할 수 있습니다.");
        Assert.isTrue(!findOrder.getStatus().equals(OrderStatus.COMPLETE), "배송 시작된 주문은 취소할수 없습니다.");

        findOrder.cancelOrder();

		List<OrderProductDto> orderProductDtos = new ArrayList<>();
		findOrder.getOrderProducts().forEach(op -> {
			orderProductDtos.add(new OrderProductDto(op.getId(), op.getOrderCount(),
					new ProductDto(productServiceClient.getProduct(op.getProductId()))));
		});

        OrderDto orderDto = new OrderDto(findOrder.getId(), customerId, this.getTotalPrice(findOrder.getId()), OrderStatus.REQUEST_SUCCESS, orderProductDtos);
        kafkaProducer.send(cancelTopic, orderDto);

    }

	@Override
    public List<OrderDto> getOrderCustomer(Long customerId) {

		return orderRepository.findOrderByCustomerIdOrderByCreatedDateDesc(customerId)
				.stream().map(OrderDto::new).toList();
    }

	@Override
    public Page<OrderDto> findOrdersByCondition(SearchCondition condition, Pageable pageable) {
        return orderRepository.findOrdersByCondition(condition, pageable).map(OrderDto::new);
    }

	@Override
    public OrderDto findOrderById(Long orderId) {
		return new OrderDto(orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND)));
    }

	@Override
	public void changeOrderStatus(Long orderId, OrderStatus status) {
		orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND))
				.changeOrderStatus(status);
	}

	@Override
    public int getTotalPrice(Long orderId){
		AtomicInteger totalPrice= new AtomicInteger();
		orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND))
				.getOrderProducts().forEach(op -> {
			int price = new ProductDto(productServiceClient.getProduct(op.getProductId())).getPrice();
			int orderCount = op.getOrderCount();

			totalPrice.addAndGet((price * orderCount));
		});

        return totalPrice.get();
    }
}
