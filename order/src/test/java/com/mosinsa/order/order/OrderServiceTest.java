package com.mosinsa.order.order;

import com.mosinsa.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


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

        OrderDto orderDto = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getProducts());

        Order createOrder = orderRepository.findById(orderDto.getId()).get();

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
        assertThat(createOrder.getOrderProducts().size()).isEqualTo(2);
        assertThat(productService.getProduct(productA.getId()).getStock()).isEqualTo(7);
        assertThat(productService.getProduct(productB.getId()).getStock()).isEqualTo(5);

    }


    @Test
    void 주문취소(){
        OrderCreateRequest orderCreateRequest = 상품주문요청_생성(customerA.getId(),productA.getId(), productB.getId());

        OrderDto orderDto = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getProducts());

        orderService.cancelOrder(orderDto.getCustomerId(), orderDto.getId());

        Order cancelOrder = orderRepository.findById(orderDto.getId()).get();

        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(cancelOrder.getOrderProducts().get(0).getProduct().getStock()).isEqualTo(10);
        assertThat(cancelOrder.getOrderProducts().get(1).getProduct().getStock()).isEqualTo(10);
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