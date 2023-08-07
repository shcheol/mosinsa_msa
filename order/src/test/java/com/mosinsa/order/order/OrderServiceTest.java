package com.mosinsa.order.order;

import com.mosinsa.order.controller.request.OrderCreateRequest;
import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.request.RequestCreateCustomer;
import com.mosinsa.order.controller.response.ResponseCustomer;
import com.mosinsa.order.controller.response.ResponseProduct;
import com.mosinsa.order.db.dto.OrderDto;
import com.mosinsa.order.db.entity.DiscountPolicy;
import com.mosinsa.order.db.entity.Order;
import com.mosinsa.order.db.entity.OrderStatus;
import com.mosinsa.order.service.feignclient.CustomerServiceClient;
import com.mosinsa.order.service.feignclient.ProductServiceClient;
import com.mosinsa.order.db.repository.OrderRepository;
import com.mosinsa.order.service.OrderService;
import org.junit.jupiter.api.AfterEach;
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

	@Autowired
    CustomerServiceClient customerServiceClient;
	@Autowired
	ProductServiceClient productServiceClient;

    @BeforeEach
    void setUp(){
		System.out.println("setUp()");
        productA = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE));
        productB = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE));

		Long customerId = customerServiceClient.createCustomer(
				new RequestCreateCustomer("9996", "1", "1", "aa@aa.com"));
		System.out.println("customerId = " + customerId);
		customerA = customerServiceClient.getCustomer(customerId);
        customerB = customerServiceClient.getCustomer(customerServiceClient.createCustomer(
				new RequestCreateCustomer("9998","2","2","aa@aa.com")));
    }

	@AfterEach
	void afterEach(){
		System.out.println("OrderServiceTest.afterEach");
		customerServiceClient.deleteCustomer(customerA.getId());
		customerServiceClient.deleteCustomer(customerB.getId());
	}

    @Test
    void 상품주문(){
		System.out.println("OrderServiceTest.상품주문");

        OrderCreateRequest orderCreateRequest = 상품주문요청_생성(customerA.getId(),productA.getProductId(), productB.getProductId());

        OrderDto orderDto = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getProducts());

        Order createOrder = orderRepository.findById(orderDto.getOrderId()).get();

        assertThat(createOrder.getStatus()).isEqualTo(OrderStatus.CREATE);
        assertThat(createOrder.getOrderProducts().size()).isEqualTo(2);
//        assertThat(productService.getProduct(productA.getId()).getStock()).isEqualTo(7);
//        assertThat(productService.getProduct(productB.getId()).getStock()).isEqualTo(5);

    }


    @Test
    void 주문취소(){
        OrderCreateRequest orderCreateRequest = 상품주문요청_생성(customerA.getId(),productA.getProductId(), productB.getProductId());

        OrderDto orderDto = orderService.order(orderCreateRequest.getCustomerId(), orderCreateRequest.getProducts());

        orderService.cancelOrder(orderDto.getCustomerId(), orderDto.getOrderId());

        Order cancelOrder = orderRepository.findById(orderDto.getOrderId()).get();

        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
//        assertThat(cancelOrder.getOrderProducts().get(0).getProduct().getStock()).isEqualTo(10);
//        assertThat(cancelOrder.getOrderProducts().get(1).getProduct().getStock()).isEqualTo(10);
    }

//    @Test
    void 주문목록조회_회원(){

        OrderCreateRequest orderCreateRequest1 = 상품주문요청_생성(customerA.getId(),productA.getProductId(), productB.getProductId());
        orderService.order(orderCreateRequest1.getCustomerId(), orderCreateRequest1.getProducts());
        OrderCreateRequest orderCreateRequest2 = 상품주문요청_생성(customerB.getId(),productA.getProductId(), productB.getProductId());
        orderService.order(orderCreateRequest2.getCustomerId(), orderCreateRequest2.getProducts());

        List<OrderDto> orderCustomer = orderService.getOrderCustomer(orderCreateRequest2.getCustomerId());

        assertThat(orderCustomer.get(0).getCustomerId()).isEqualTo(customerB.getId());
        assertThat(orderCustomer.get(0).getOrderProducts().size()).isEqualTo(2);
    }

}