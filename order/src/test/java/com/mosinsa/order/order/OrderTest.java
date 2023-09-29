package com.mosinsa.order.order;

import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.response.ResponseCustomer;
import com.mosinsa.order.controller.response.ResponseProduct;
import com.mosinsa.order.db.entity.DiscountPolicy;
import com.mosinsa.order.db.entity.Order;
import com.mosinsa.order.db.entity.OrderProduct;
import com.mosinsa.order.db.entity.OrderStatus;
import com.mosinsa.order.service.feignclient.CustomerServiceClient;
import com.mosinsa.order.service.feignclient.ProductServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class OrderTest {
    ResponseProduct productA;
    ResponseProduct productB;

    ResponseCustomer customer;

	@Autowired
    ProductServiceClient productServiceClient;
	@Autowired
    CustomerServiceClient customerServiceClient;


    @BeforeEach
    void setup(){

        productA = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE));
        productB = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE));

        customer= customerServiceClient.getCustomer(new HashMap<>(),"1");
    }

//    @Test
    void 주문생성() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        orderProduct.add(OrderProduct.createOrderProduct(productA.getProductId(),5));
        orderProduct.add(OrderProduct.createOrderProduct(productB.getProductId(),10));

        Order createOrder = Order.createOrder(customer.getId(), orderProduct);

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
        assertThat(createOrder.getOrderProducts()).hasSize(2);
        assertThat(productServiceClient.getProduct(new HashMap<>(), productA.getProductId()).getStock()).isEqualTo(5);
        assertThat(productServiceClient.getProduct(new HashMap<>(), productB.getProductId()).getStock()).isZero();

    }

//    @Test
    void 주문생성_실패_주문상품x() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        String id = customer.getId();
        assertThatThrownBy(()->Order.createOrder(id, orderProduct)).isInstanceOf(RuntimeException.class);

        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);
    }

//    @Test
    void 주문생성_주문상품_수량초과() {
        ArrayList<OrderProduct> orderProduct = new ArrayList<>();
        String productId = productA.getProductId();
        assertThatThrownBy(()->
				orderProduct.add(OrderProduct.createOrderProduct(productId,11)))
				.isInstanceOf(RuntimeException.class);

        assertThat(productA.getStock()).isEqualTo(10);
        assertThat(productB.getStock()).isEqualTo(10);
    }


//    @Test
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