package com.shopping.mosinsa.order;

import com.shopping.mosinsa.controller.request.OrderCreateRequest;
import com.shopping.mosinsa.dto.OrderDto;
import com.shopping.mosinsa.dto.ProductDto;
import com.shopping.mosinsa.entity.*;
import com.shopping.mosinsa.repository.CustomerRepository;
import com.shopping.mosinsa.repository.OrderRepository;
import com.shopping.mosinsa.repository.ProductRepository;
import com.shopping.mosinsa.service.OrderService;
import com.shopping.mosinsa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.shopping.mosinsa.order.OrderSteps.*;
import static com.shopping.mosinsa.product.ProductSteps.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    Customer customerA;
    Customer customerB;
    ProductDto productA;
    ProductDto productB;

    @BeforeEach
    void setUp(){
        productA = productService.addProduct(상품등록요청_생성());
        productB = productService.addProduct(상품등록요청_생성());

        customerA = new Customer(CustomerGrade.BRONZE);
        customerRepository.save(customerA);
        customerB = new Customer(CustomerGrade.BRONZE);
        customerRepository.save(customerB);

    }

    @Test
    void 상품주문(){

        OrderCreateRequest orderCreateRequest = 상품주문요청_생성(customerA.getId(),productA.getId(), productB.getId());

        Long orderId = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getOrderProducts());

        Order createOrder = orderRepository.findById(orderId).get();

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
        assertThat(createOrder.getOrderProducts().size()).isEqualTo(2);
        assertThat(productService.getProduct(productA.getId()).getStock()).isEqualTo(7);
        assertThat(productService.getProduct(productB.getId()).getStock()).isEqualTo(5);

    }


    @Test
    void 주문취소(){
        OrderCreateRequest orderCreateRequest = 상품주문요청_생성(customerA.getId(),productA.getId(), productB.getId());

        Long orderId = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getOrderProducts());

        orderService.cancelOrder(orderId);

        Order cancelOrder = orderRepository.findById(orderId).get();

        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(cancelOrder.getOrderProducts().get(0).getProduct().getStock()).isEqualTo(10);
        assertThat(cancelOrder.getOrderProducts().get(1).getProduct().getStock()).isEqualTo(10);
    }

    @Test
    void 주문목록조회_회원(){

        OrderCreateRequest orderCreateRequest1 = 상품주문요청_생성(customerA.getId(),productA.getId(), productB.getId());
        orderService.order(orderCreateRequest1.getCustomerId(), orderCreateRequest1.getOrderProducts());
        OrderCreateRequest orderCreateRequest2 = 상품주문요청_생성(customerB.getId(),productA.getId(), productB.getId());
        orderService.order(orderCreateRequest2.getCustomerId(), orderCreateRequest2.getOrderProducts());

        List<OrderDto> orderCustomer = orderService.getOrderCustomer(orderCreateRequest2.getCustomerId());

        System.out.println(orderCustomer.get(0).getOrderProducts().toString());
        assertThat(orderCustomer.get(0).getCustomerId()).isEqualTo(customerB.getId());
        assertThat(orderCustomer.get(0).getOrderProducts().size()).isEqualTo(2);
    }

}