package com.mosinsa.order.application;


import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.ui.request.SearchCondition;
import com.mosinsa.order.ui.response.ResponseCustomer;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.dto.OrderProductDto;
import com.mosinsa.order.dto.ProductDto;
import com.mosinsa.order.db.Order;
import com.mosinsa.order.db.OrderProduct;
import com.mosinsa.order.db.OrderStatus;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.infra.feignclient.CustomerServiceClient;
import com.mosinsa.order.infra.feignclient.ProductServiceClient;
import com.mosinsa.order.infra.kafka.KafkaProducer;
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
    public OrderDto order(String customerId, Map<String, Integer> productMap, Map<String, Collection<String>> authMap) {
        Assert.isTrue(productMap.size()>=1, "1개 이상의 상품을 주문해야 합니다.");

		log.info("header map {}", authMap);
		ResponseCustomer customer =customerServiceClient.getCustomer(authMap, customerId);

		List<OrderProduct> orderProductList = new ArrayList<>();
        List<OrderProductDto> orderProductDtos = new ArrayList<>();

		productMap.keySet().forEach(productId ->{
			orderProductList.add(OrderProduct.createOrderProduct(productId, productMap.get(productId)));
			orderProductDtos.add(
					new OrderProductDto(
							OrderProduct.createOrderProduct(productId, productMap.get(productId)).getId(),
							productMap.get(productId), new ProductDto(productServiceClient.getProduct(authMap, productId))));
		});

		Order order = orderRepository.save(Order.createOrder(customer.getId(), orderProductList));
		OrderDto orderDto = new OrderDto(order.getId(), customer.getId(), getTotalPrice(order.getId(), authMap), OrderStatus.CREATE, orderProductDtos);
		kafkaProducer.send(orderTopic, orderDto);

		return orderDto;
    }

	@Override
    @Transactional
    public void cancelOrder(String customerId, Long orderId, Map<String, Collection<String>> authMap) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));
		Assert.isTrue(Objects.equals(findOrder.getCustomerId(), customerId), "주문한 고객과 동일한 고객이 취소해야합니다.");

		OrderStatus status = findOrder.getStatus();
        Assert.isTrue(!status.equals(OrderStatus.CANCEL),"이미 취소처리된 주문입니다.");

		String orderCustomer = findOrder.getCustomerId();
        Assert.isTrue(orderCustomer.equals(customerId),"본인이 주문한 주문만 취소 할 수 있습니다.");
        Assert.isTrue(!findOrder.getStatus().equals(OrderStatus.COMPLETE), "배송 시작된 주문은 취소할수 없습니다.");

        findOrder.cancelOrder();

		List<OrderProductDto> orderProductDtos = new ArrayList<>();
		findOrder.getOrderProducts().forEach(op -> orderProductDtos.add(new OrderProductDto(op.getId(), op.getOrderCount(),
				new ProductDto(productServiceClient.getProduct(authMap, op.getProductId())))));

        OrderDto orderDto = new OrderDto(findOrder.getId(), customerId, this.getTotalPrice(findOrder.getId(),authMap), OrderStatus.REQUEST_SUCCESS, orderProductDtos);
        kafkaProducer.send(cancelTopic, orderDto);

    }

	@Override
    public List<OrderDto> getOrderCustomer(String customerId) {

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

//	@Override
    private int getTotalPrice(Long orderId, Map<String, Collection<String>> authMap){
		AtomicInteger totalPrice= new AtomicInteger();
		orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND))
				.getOrderProducts().forEach(op -> {
			int price = new ProductDto(productServiceClient.getProduct(authMap, op.getProductId())).getPrice();
			int orderCount = op.getOrderCount();

			totalPrice.addAndGet((price * orderCount));
		});

        return totalPrice.get();
    }
}
