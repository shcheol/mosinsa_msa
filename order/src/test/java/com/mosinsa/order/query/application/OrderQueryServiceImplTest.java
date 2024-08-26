package com.mosinsa.order.query.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.common.argumentresolver.CustomerInfo;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.infra.stub.ExternalApiObjectFactory;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql("classpath:db/test-init.sql")
@Import(ExternalApiObjectFactory.class)
class OrderQueryServiceImplTest {

	@Autowired
	OrderQueryServiceImpl orderQueryService;
	ObjectMapper om;
	OrderConfirmRequest orderConfirmRequestWithCoupon;
	OrderConfirmRequest orderConfirmRequestWithoutCoupon;
	CustomerInfo customerInfo = new CustomerInfo("customerId", "name");
    @Test
    void findMyOrdersByConditionEx() {
        SearchCondition searchCondition1 = new SearchCondition("", null);
        PageRequest pageable = PageRequest.of(0, 3);
        assertThrows(OrderException.class, () -> orderQueryService.findMyOrdersByCondition(searchCondition1, pageable));

		SearchCondition searchCondition2 = new SearchCondition(null, null);
		assertThrows(OrderException.class, () -> orderQueryService.findMyOrdersByCondition(searchCondition2, pageable));
    }

	@Test
	void findMyOrdersByCondition() {
		SearchCondition searchCondition1 = new SearchCondition("customer1", null);
		PageRequest pageable = PageRequest.of(0, 3);
		Page<OrderSummary> myOrdersByCondition = orderQueryService.findMyOrdersByCondition(searchCondition1, pageable);
		assertThat(myOrdersByCondition.getTotalElements()).isEqualTo(2);
		List<String> ids = myOrdersByCondition.getContent().stream().map(OrderSummary::getOrderId).toList();
		assertThat(ids).contains("orderId1","orderId2");
	}

    @Test
    void getOrderDetails() {
        OrderDetail orderId2 = orderQueryService.getOrderDetails("orderId2");
        assertThat(orderId2.getOrderId()).isEqualTo("orderId2");
    }

    @Test
    void getOrderDetailsNotFound() {
        assertThrows(OrderException.class, () -> orderQueryService.getOrderDetails("orderId2xxx"));
    }

    @Test
    void getOrderDetailsEx() {
        assertThrows(IllegalArgumentException.class, () -> orderQueryService.getOrderDetails(""));
    }



	@Test
	void orderConfirmSuccessWithCoupon() {

		OrderConfirmDto orderConfirmDto = orderQueryService.orderConfirm(customerInfo, orderConfirmRequestWithCoupon);
		assertThat(orderConfirmDto.orderProducts()).hasSize(1);
		assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
		assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
		assertThat(orderConfirmDto.couponId()).isEqualTo("couponId");
		assertThat(orderConfirmDto.totalAmount()).isEqualTo(1800);
	}

	@Test
	void orderConfirmCouponServiceErrorOrderWithoutCoupon() {

		OrderConfirmDto orderConfirmDto = orderQueryService.orderConfirm(customerInfo, orderConfirmRequestWithoutCoupon);
		assertThat(orderConfirmDto.orderProducts()).hasSize(1);
		assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
		assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
		assertThat(orderConfirmDto.couponId()).isNull();
		assertThat(orderConfirmDto.totalAmount()).isEqualTo(2000);
	}

	@BeforeEach
	void init() throws JsonProcessingException {
		om = new ObjectMapper();
		String s = """
                {
                    "couponId":"couponId",
                    "myOrderProducts":[
                        {
                            "productId":"productId",
                            "quantity":2
                        }
                        ],
                        "shippingInfo":{
                            "message":"home",
                            "address":{
                                "zipCode":"zipcode",
                                "address1":"address1",
                                "address2":"address2"
                            },
                            "receiver":{
                                "name":"myname",
                                "phoneNumber":"010-1111-1111"
                            }
                        }
                }
                """;
		orderConfirmRequestWithCoupon = om.readValue(s, OrderConfirmRequest.class);

		String noCoupon = """
                {
                    "myOrderProducts":[
                        {
                            "productId":"productId",
                            "quantity":2
                        }
                        ],
                        "shippingInfo":{
                            "message":"home",
                            "address":{
                                "zipCode":"zipcode",
                                "address1":"address1",
                                "address2":"address2"
                            },
                            "receiver":{
                                "name":"myname",
                                "phoneNumber":"010-1111-1111"
                            }
                        }
                }
                """;
		orderConfirmRequestWithoutCoupon = om.readValue(noCoupon, OrderConfirmRequest.class);
	}
}