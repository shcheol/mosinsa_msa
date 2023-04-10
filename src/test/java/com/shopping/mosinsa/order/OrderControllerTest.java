package com.shopping.mosinsa.order;

import com.shopping.mosinsa.ApiTest;
import com.shopping.mosinsa.entity.Customer;
import com.shopping.mosinsa.entity.CustomerGrade;
import com.shopping.mosinsa.entity.OrderStatus;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;

import static com.shopping.mosinsa.order.OrderSteps.*;
import static com.shopping.mosinsa.product.ProductSteps.상품등록요청;
import static com.shopping.mosinsa.product.ProductSteps.상품등록요청_생성;
import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerTest extends ApiTest {

    private Long customerId;

    @BeforeEach
    public void init() {
        Customer customer = new Customer(CustomerGrade.BRONZE);
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

        Long id1 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
        Long id2 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");

        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }


    @Test
    void 주문취소() {

        Long id1 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
        Long id2 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
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
        Long id1 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
        Long id2 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");

        ExtractableResponse<Response> response = 상품주문(상품주문요청_생성(customerId, id1, id2));
        long orderId = response.body().jsonPath().getLong("id");

        ExtractableResponse<Response> orderResponse = 주문조회요청(orderId);
        assertThat(orderResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(orderResponse.body().jsonPath().getString("status")).isEqualTo(OrderStatus.CREATE.toString());


    }




}