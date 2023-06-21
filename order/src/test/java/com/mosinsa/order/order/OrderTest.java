package com.mosinsa.order.order;

import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.response.ResponseCustomer;
import com.mosinsa.order.controller.response.ResponseProduct;
import com.mosinsa.order.entity.DiscountPolicy;
import com.mosinsa.order.entity.Order;
import com.mosinsa.order.entity.OrderProduct;
import com.mosinsa.order.entity.OrderStatus;
import com.mosinsa.order.feignclient.CustomerServiceClient;
import com.mosinsa.order.feignclient.ProductServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class OrderTest {
    ResponseProduct productA;
    ResponseProduct productB;

    ResponseCustomer customer;

    ProductServiceClient productServiceClient;
    CustomerServiceClient customerServiceClient;


    @BeforeEach
    void setup(){

        productA = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE));
        productB = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE));

        customer= customerServiceClient.getCustomer(1L);
    }

    @Test
    void 주문생성() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        orderProduct.add(OrderProduct.createOrderProduct(productA.getProductId(),5));
        orderProduct.add(OrderProduct.createOrderProduct(productB.getProductId(),10));

        Order createOrder = Order.createOrder(customer.getId(), orderProduct);

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
        assertThat(createOrder.getOrderProducts().size()).isEqualTo(2);
        assertThat(productA.getStock()).isEqualTo(5);
        assertThat(productB.getStock()).isEqualTo(0);

    }

    @Test
    void 주문생성_실패_주문상품x() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();

        assertThatThrownBy(()->Order.createOrder(customer.getId(), orderProduct)).isInstanceOf(IllegalArgumentException.class);

        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);
    }

    @Test
    void 주문생성_주문상품_수량초과() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();

        assertThatThrownBy(()->orderProduct.add(OrderProduct.createOrderProduct(productA.getProductId(),11))).isInstanceOf(IllegalArgumentException.class);

        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);
    }


    @Test
    void 주문취소() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        orderProduct.add(OrderProduct.createOrderProduct(productA.getProductId(),5));
        orderProduct.add(OrderProduct.createOrderProduct(productB.getProductId(),10));

        Order createOrder = Order.createOrder(customer.getId(), orderProduct);

        createOrder.cancelOrder();

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);

    }

//    @Test
//    void 가격조회(){
//        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
//        orderProduct.add(OrderProduct.createOrderProduct(productA.getId(),5));
//        orderProduct.add(OrderProduct.createOrderProduct(productB.getId(),10));
//
//        Order createOrder = Order.createOrder(customer.getId(), orderProduct);
//
//        int totalPrice = createOrder.getTotalPrice();
//        assertThat(totalPrice).isEqualTo(23000);
//    }
}