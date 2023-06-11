package com.mosinsa.order.order;


import com.mosinsa.order.ApiTest;
import com.mosinsa.order.controller.request.ProductAddRequest;
import com.mosinsa.order.controller.request.RequestCreateCustomer;
import com.mosinsa.order.controller.response.ResponseCustomer;
import com.mosinsa.order.controller.response.ResponseProduct;
import com.mosinsa.order.entity.DiscountPolicy;
import com.mosinsa.order.entity.OrderStatus;
import com.mosinsa.order.feignclient.CustomerServiceClient;
import com.mosinsa.order.feignclient.ProductServiceClient;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.mosinsa.order.order.OrderSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerTest extends ApiTest {

    private Long customerId;

    ProductServiceClient productServiceClient;
    @BeforeEach
    public void init() {
        RequestCreateCustomer customer = new RequestCreateCustomer("1","1","1","aa@aa.com");
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customer)
                .when()
                .post("/customer")
                .then().log().all().extract();
        customerId = response.body().jsonPath().getLong("id");

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
        Long id1 = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE)).getId();
        Long id2 = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE)).getId();

        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }


    @Test
    void 주문취소() {
        Long id1 = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE)).getId();
        Long id2 = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE)).getId();
        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));

        long orderId = response.body().jsonPath().getLong("id");


        ExtractableResponse<Response> cancelResponse = 주문취소요청(주문취소요청_생성(customerId, orderId));

        assertThat(cancelResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> orderResponse = 주문조회요청(orderId);

        assertThat(orderResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponse.body().jsonPath().getString("status")).isEqualTo(OrderStatus.CANCEL.toString());


    }

    @Test
    public void 주문단건조회(){

        Long id1 = productServiceClient.addProduct(new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE)).getId();
        Long id2 = productServiceClient.addProduct(new ProductAddRequest("상품2", 2000, 10, DiscountPolicy.TEN_PERCENTAGE)).getId();

        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));
        long orderId = response.body().jsonPath().getLong("id");

        ExtractableResponse<Response> orderResponse = 주문조회요청(orderId);
        assertThat(orderResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponse.body().jsonPath().getString("status")).isEqualTo(OrderStatus.CREATE.toString());


    }




}