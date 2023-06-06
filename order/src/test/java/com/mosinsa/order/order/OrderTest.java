package com.mosinsa.order.order;

import com.mosinsa.order.entity.Order;
import com.mosinsa.order.entity.OrderProduct;
import com.mosinsa.order.entity.OrderStatus;
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
    Product productA;
    Product productB;

    @BeforeEach
    void setup(){
        productA = new Product("상품1", 1000, 10, DiscountPolicy.NONE);
        productB = new Product("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE);

    }

    @Test
    void 주문생성() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        orderProduct.add(OrderProduct.createOrderProduct(productA,5));
        orderProduct.add(OrderProduct.createOrderProduct(productB,10));

        Order createOrder = Order.createOrder(new Customer(CustomerGrade.BRONZE), orderProduct);

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
        assertThat(createOrder.getOrderProducts().size()).isEqualTo(2);
        assertThat(productA.getStock()).isEqualTo(5);
        assertThat(productB.getStock()).isEqualTo(0);

    }

    @Test
    void 주문생성_실패_주문상품x() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();

        assertThatThrownBy(()->Order.createOrder(new Customer(CustomerGrade.BRONZE), orderProduct)).isInstanceOf(IllegalArgumentException.class);

        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);
    }

    @Test
    void 주문생성_주문상품_수량초과() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();

        assertThatThrownBy(()->orderProduct.add(OrderProduct.createOrderProduct(productA,11))).isInstanceOf(IllegalArgumentException.class);

        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);
    }


    @Test
    void 주문취소() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        orderProduct.add(OrderProduct.createOrderProduct(productA,5));
        orderProduct.add(OrderProduct.createOrderProduct(productB,10));

        Order createOrder = Order.createOrder(new Customer(CustomerGrade.BRONZE), orderProduct);

        createOrder.cancelOrder();

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);

    }

    @Test
    void 가격조회(){
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        orderProduct.add(OrderProduct.createOrderProduct(productA,5));
        orderProduct.add(OrderProduct.createOrderProduct(productB,10));

        Order createOrder = Order.createOrder(new Customer(CustomerGrade.BRONZE), orderProduct);

        int totalPrice = createOrder.getTotalPrice();
        assertThat(totalPrice).isEqualTo(23000);
    }
}