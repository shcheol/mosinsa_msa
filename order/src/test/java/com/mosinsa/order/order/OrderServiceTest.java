package com.mosinsa.order.order;

import com.mosinsa.order.controller.request.OrderCreateRequest;
import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.response.ResponseCustomer;
import com.mosinsa.order.controller.response.ResponseProduct;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.entity.DiscountPolicy;
import com.mosinsa.order.entity.Order;
import com.mosinsa.order.entity.OrderStatus;
import com.mosinsa.order.feignclient.CustomerServiceClient;
import com.mosinsa.order.feignclient.ProductServiceClient;
import com.mosinsa.order.repository.OrderRepository;
import com.mosinsa.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mosinsa.order.order.OrderSteps.상품주문요청_생성;
import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
//    @Autowired
//    ProductService productService;
//
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @Autowired
//    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    ResponseCustomer customerA;
    ResponseCustomer customerB;
    ResponseProduct productA;
    ResponseProduct productB;

    CustomerServiceClient customerServiceClient;
    ProductServiceClient productServiceClient;
    @BeforeEach
    void setUp(){
        productA = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE));
        productB = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE));

        customerA = customerServiceClient.getCustomer(1L);
        customerB = customerServiceClient.getCustomer(2L);
    }

    @Test
    void 상품주문(){

        OrderCreateRequest orderCreateRequest = 상품주문요청_생성(customerA.getId(),productA.getId(), productB.getId());

        OrderDto orderDto = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getProducts());

        Order createOrder = orderRepository.findById(orderDto.getId()).get();

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
        assertThat(createOrder.getOrderProducts().size()).isEqualTo(2);
//        assertThat(productService.getProduct(productA.getId()).getStock()).isEqualTo(7);
//        assertThat(productService.getProduct(productB.getId()).getStock()).isEqualTo(5);

    }


    @Test
    void 주문취소(){
        OrderCreateRequest orderCreateRequest = 상품주문요청_생성(customerA.getId(),productA.getId(), productB.getId());

        OrderDto orderDto = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getProducts());

        orderService.cancelOrder(orderDto.getCustomerId(), orderDto.getId());

        Order cancelOrder = orderRepository.findById(orderDto.getId()).get();

        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
//        assertThat(cancelOrder.getOrderProducts().get(0).getProduct().getStock()).isEqualTo(10);
//        assertThat(cancelOrder.getOrderProducts().get(1).getProduct().getStock()).isEqualTo(10);
    }

    @Test
    void 주문목록조회_회원(){

        OrderCreateRequest orderCreateRequest1 = 상품주문요청_생성(customerA.getId(),productA.getId(), productB.getId());
        orderService.order(orderCreateRequest1.getCustomerId(), orderCreateRequest1.getProducts());
        OrderCreateRequest orderCreateRequest2 = 상품주문요청_생성(customerB.getId(),productA.getId(), productB.getId());
        orderService.order(orderCreateRequest2.getCustomerId(), orderCreateRequest2.getProducts());

        List<OrderDto> orderCustomer = orderService.getOrderCustomer(orderCreateRequest2.getCustomerId());

        assertThat(orderCustomer.get(0).getCustomerId()).isEqualTo(customerB.getId());
        assertThat(orderCustomer.get(0).getOrderProducts().size()).isEqualTo(2);
    }

}