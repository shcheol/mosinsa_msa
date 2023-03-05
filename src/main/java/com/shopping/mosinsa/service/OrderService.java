package com.shopping.mosinsa.service;

import com.shopping.mosinsa.controller.request.OrderCreateRequest;
import com.shopping.mosinsa.dto.ProductDto;
import com.shopping.mosinsa.entity.*;
import com.shopping.mosinsa.repository.CustomerRepository;
import com.shopping.mosinsa.repository.OrderProductRepository;
import com.shopping.mosinsa.repository.OrderRepository;
import com.shopping.mosinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    private final OrderRepository orderRepository;

    @Transactional
    public void addOrderProduct(Order order, ProductDto productDto, int requestCount) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));

        OrderProduct save = orderProductRepository.save(new OrderProduct(order, product,requestCount));

    }

    @Transactional
    public Order createOrderService(OrderCreateRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));

        Order order = new Order(customer);

        return orderRepository.save(order);
    }

    public void orderProducts(OrderCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));

    }

    @Transactional
    public Order submitOrder(Order order) {
        Assert.isTrue(order.getOrderProducts().size()>0, "1개 이상의 상품을 주문해야 합니다.");
        Assert.isTrue(order.getStatus().equals(OrderStatus.CREATE), "취소되거나 배송완료된 주문입니다.");

        Order findOrder = orderRepository.findById(order.getId()).orElseThrow(() -> new IllegalArgumentException("요청한 주문이 없습니다."));

        List<OrderProduct> orderProducts = findOrder.getOrderProducts();
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.getProduct().removeStock(orderProduct.getRequestCount());
        }

        findOrder.changeOrderStatus(OrderStatus.REQUEST_SUCCESS);

        return findOrder;
    }

    @Transactional
    public Order cancelOrder(Order order) {
        Assert.isTrue(order.getStatus().equals(OrderStatus.REQUEST_SUCCESS), "배송시작되거나 주문 성공하지 않은 주문은 취소할수 없습니다.");

        Order findOrder = orderRepository.findById(order.getId()).orElseThrow(() -> new IllegalArgumentException("취소 요청한 주문이 없습니다."));

        List<OrderProduct> orderProducts = findOrder.getOrderProducts();
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.getProduct().addStock(orderProduct.getRequestCount());
        }

        findOrder.changeOrderStatus(OrderStatus.CANCEL);

        return findOrder;
    }
}
