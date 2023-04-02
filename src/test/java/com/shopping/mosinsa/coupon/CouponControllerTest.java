package com.shopping.mosinsa.coupon;

import com.shopping.mosinsa.ApiTest;
import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.entity.Customer;
import com.shopping.mosinsa.entity.CustomerGrade;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.shopping.mosinsa.coupon.CouponSteps.쿠폰이벤트생성;
import static com.shopping.mosinsa.coupon.CouponSteps.쿠폰이벤트생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CouponControllerTest extends ApiTest {

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

    @Test
    void createEvent() {
        int stock = 50;

        ExtractableResponse<Response> response = 쿠폰이벤트생성(쿠폰이벤트생성_요청(stock));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

    }


    @Test
    void issuanceRequest() {
        int stock = 50;
        ExtractableResponse<Response> couponEventResponse = 쿠폰이벤트생성(쿠폰이벤트생성_요청(stock));
        CouponIssuanceRequest request = CouponSteps.쿠폰발급요청_생성(customerId, couponEventResponse.body().jsonPath().getLong("id"));

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .patch("/coupon")
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}