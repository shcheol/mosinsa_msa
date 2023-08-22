package com.mosinsa.order.order;


import com.mosinsa.order.ApiTest;
import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.request.RequestCreateCustomer;
import com.mosinsa.order.db.entity.DiscountPolicy;
import com.mosinsa.order.db.entity.OrderStatus;
import com.mosinsa.order.service.feignclient.CustomerServiceClient;
import com.mosinsa.order.service.feignclient.ProductServiceClient;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.mosinsa.order.order.OrderSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerTest extends ApiTest {

    private String customerId;
	@Autowired
    ProductServiceClient productServiceClient;

	@Autowired
	CustomerServiceClient customerServiceClient;

    @BeforeEach
    public void init() {
		customerId = customerServiceClient.createCustomer(
				new RequestCreateCustomer("9997","1","1","aa@aa.com"));
	}

//    @Test
    void 회원주문목록() {

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/orders?=" + customerId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 주문목록() {

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/orders")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 주문하기() {
        String id1 = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE)).getProductId();
        String id2 = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE)).getProductId();

        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }


    @Test
    void 주문취소() {
        String id1 = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE)).getProductId();
        String id2 = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE)).getProductId();
        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));

        long orderId = response.body().jsonPath().getLong("orderId");


        ExtractableResponse<Response> cancelResponse = 주문취소요청(주문취소요청_생성(customerId, orderId));

        assertThat(cancelResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> orderResponse = 주문조회요청(orderId);

        assertThat(orderResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponse.body().jsonPath().getString("status")).isEqualTo(OrderStatus.CANCEL.toString());


    }

    @Test
    public void 주문단건조회(){

        String id1 = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE)).getProductId();
        String id2 = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE)).getProductId();

        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));
        long orderId = response.body().jsonPath().getLong("orderId");

        ExtractableResponse<Response> orderResponse = 주문조회요청(orderId);
        assertThat(orderResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponse.body().jsonPath().getString("status")).isEqualTo(OrderStatus.REQUEST_SUCCESS.toString());


    }




}