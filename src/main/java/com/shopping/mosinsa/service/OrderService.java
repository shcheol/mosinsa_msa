package com.shopping.mosinsa.service;

import com.shopping.mosinsa.dto.OrderDto;
import com.shopping.mosinsa.entity.*;
import com.shopping.mosinsa.repository.CustomerRepository;
import com.shopping.mosinsa.repository.OrderProductRepository;
import com.shopping.mosinsa.repository.OrderRepository;
import com.shopping.mosinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public Long order(Long customerId, Map<Long, Integer> productMap) {
        Assert.isTrue(productMap.size()>=1, "1개 이상의 상품을 주문해야 합니다.");

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("누구세요"));

        ArrayList<OrderProduct> orderProductList = new ArrayList<>();
        for (Long productId : productMap.keySet()) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, productMap.get(productId));
            orderProductList.add(orderProduct);
        }

        Order order = Order.createOrder(customer, orderProductList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("취소 요청한 주문이 없습니다."));

        Assert.isTrue(!findOrder.getStatus().equals(OrderStatus.COMPLETE), "배송 시작된 주문은 취소할수 없습니다.");

        findOrder.cancelOrder();
    }

    public List<OrderDto> getOrderCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("누구세요"));
        List<Order> orders = orderRepository.findByCustomerOrderByCreatedDateDesc(customer);

        return orders.stream().map(OrderDto::new).toList();
    }

    public Page<OrderDto> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderDto::new);
    }
}
