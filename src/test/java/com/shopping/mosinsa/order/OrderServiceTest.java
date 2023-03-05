package com.shopping.mosinsa.order;

import com.shopping.mosinsa.controller.request.OrderCreateRequest;
import com.shopping.mosinsa.dto.ProductDto;
import com.shopping.mosinsa.entity.*;
import com.shopping.mosinsa.repository.CustomerRepository;
import com.shopping.mosinsa.service.OrderService;
import com.shopping.mosinsa.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    Customer customer;
    ProductDto productA;
    ProductDto productB;
    ProductDto productC;

    @BeforeEach
    void setUp(){
        productA = productService.addProduct(상품등록요청_생성());
        productB = productService.addProduct(상품등록요청_생성());
        productC = productService.addProduct(상품등록요청_생성());

        customer = new Customer(CustomerGrade.BRONZE);
        customerRepository.save(customer);

    }

    @Test
    void 상품주문생성(){

        Order createOrder = orderService.createOrderService(new OrderCreateRequest(customer.getId()));

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
    }

    @Test
    void 상품주문(){
        Order createOrder = orderService.createOrderService(new OrderCreateRequest(customer.getId()));

        orderService.addOrderProduct(createOrder, productA,3);
        orderService.addOrderProduct(createOrder, productB,3);

        Order submitOrder = orderService.submitOrder(createOrder);

        assertThat(submitOrder.getStatus()).isEqualTo(OrderStatus.REQUEST_SUCCESS);
        assertThat(submitOrder.getOrderProducts().size()).isEqualTo(2);
        assertThat(productService.getProduct(productA.getId()).getStock()).isEqualTo(7);
    }

    @Test
    void 상품주문_실패_주문상품0개(){
        Order createOrder = orderService.createOrderService(new OrderCreateRequest(customer.getId()));

        assertThatThrownBy(() -> orderService.submitOrder(createOrder)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품주문_실패_상품수량부족(){
        Order createOrder = orderService.createOrderService(new OrderCreateRequest(customer.getId()));

        orderService.addOrderProduct(createOrder, productA,11);
        orderService.addOrderProduct(createOrder, productB,12);

        assertThatThrownBy(() -> orderService.submitOrder(createOrder)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문취소(){
        Order createOrder = orderService.createOrderService(new OrderCreateRequest(customer.getId()));

        orderService.addOrderProduct(createOrder, productA,3);
        orderService.addOrderProduct(createOrder, productB,3);

        Order submitOrder = orderService.submitOrder(createOrder);


        Order cancelOrder = orderService.cancelOrder(submitOrder);

        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(cancelOrder.getOrderProducts().size()).isEqualTo(2);


    }

    @Test
    void 주문완료(){
        // 결제, 배송
    }

}