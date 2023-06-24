package com.mosinsa.order.service;


import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.dto.OrderProductDto;
import com.mosinsa.order.dto.ProductDto;
import com.mosinsa.order.entity.Order;
import com.mosinsa.order.entity.OrderProduct;
import com.mosinsa.order.entity.OrderStatus;
import com.mosinsa.order.feignclient.ProductServiceClient;
import com.mosinsa.order.messagequeue.KafkaProducer;
import com.mosinsa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

//    private final CustomerRepository customerRepository;
//    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

    private final KafkaProducer kafkaProducer;


    @Transactional
    public OrderDto order(Long customerId, Map<String, Integer> productMap) {
        Assert.isTrue(productMap.size()>=1, "1개 이상의 상품을 주문해야 합니다.");

        List<OrderProduct> orderProductList = new ArrayList<>();
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for (String productId : productMap.keySet()) {
//            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
            ProductDto productDto = new ProductDto(productServiceClient.getProduct(productId));
            log.info("response product: {}",productDto);
            OrderProduct orderProduct = OrderProduct.createOrderProduct(productId, productMap.get(productId));
            orderProductList.add(orderProduct);
            OrderProductDto orderProductDto = new OrderProductDto(orderProduct.getId(), productMap.get(productId), productDto);
            orderProductDtos.add(orderProductDto);
        }


        Order order = Order.createOrder(customerId, orderProductList);
        orderRepository.save(order);
        log.info("order {}", order.toString());
        OrderDto orderDto = new OrderDto(order.getId(), customerId, this.getTotalPrice(order.getId()), OrderStatus.REQUEST_SUCCESS, orderProductDtos);
        log.info("orderDto {}", orderDto);
        for (OrderProductDto orderProductDto : orderProductDtos) {
            log.info("orderProductDto {}", orderProductDto);
        }
        log.info("size {}", orderProductDtos.size());
        kafkaProducer.send("mosinsa-product-order", orderDto);
        return orderDto;
    }

    @Transactional
    public void cancelOrder(Long customerId, Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("취소 요청한 주문이 없습니다."));
        OrderStatus status = findOrder.getStatus();
        Assert.isTrue(!status.equals(OrderStatus.CANCEL),"이미 취소처리된 주문입니다.");

        Long orderCustomer = findOrder.getCustomerId();
        Assert.isTrue(orderCustomer.equals(customerId),"본인이 주문한 주문만 취소 할 수 있습니다.");
        Assert.isTrue(!findOrder.getStatus().equals(OrderStatus.COMPLETE), "배송 시작된 주문은 취소할수 없습니다.");

        findOrder.cancelOrder();
        List<OrderProduct> orderProducts = findOrder.getOrderProducts();
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            ProductDto productDto = new ProductDto(productServiceClient.getProduct(orderProduct.getProductId()));
            OrderProductDto orderProductDto = new OrderProductDto(orderProduct.getId(), orderProduct.getOrderCount(), productDto);
            orderProductDtos.add(orderProductDto);
        }
        OrderDto orderDto = new OrderDto(findOrder.getId(), customerId, this.getTotalPrice(findOrder.getId()), OrderStatus.REQUEST_SUCCESS, orderProductDtos);
        kafkaProducer.send("mosinsa-product-order-cancel", orderDto);

    }

    public List<OrderDto> getOrderCustomer(Long customerId) {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("누구세요"));
        List<Order> orders = orderRepository.findOrderByCustomerIdOrderByCreatedDateDesc(customerId);

        return orders.stream().map(OrderDto::new).toList();
    }

    public Page<OrderDto> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderDto::new);
    }

    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));
        return new OrderDto(order);
    }

    public int getTotalPrice(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));

        List<OrderProduct> orderProducts = order.getOrderProducts();
        int totalPrice=0;
        for (OrderProduct orderProduct : orderProducts) {
            ProductDto productDto = new ProductDto(productServiceClient.getProduct(orderProduct.getProductId()));
            int price = productDto.getPrice();
            int orderCount = orderProduct.getOrderCount();
            totalPrice+=(price*orderCount);
        }

        return totalPrice;
    }
}
