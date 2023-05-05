package com.shopping.mosinsa.coupon;

import com.shopping.mosinsa.ApiTest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.shopping.mosinsa.coupon.CouponSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

class CouponControllerTest extends ApiTest {


    @Test
    void createEvent() {
        int stock = 50;

        ExtractableResponse<Response> response = 쿠폰이벤트생성(쿠폰이벤트생성_요청(stock));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

    }
    @Test
    void issuanceRequest() {
        int stock = 50;
        JsonPath jsonPath = 쿠폰이벤트생성(쿠폰이벤트생성_요청(stock)).body().jsonPath();
        CouponIssuanceRequest request = 쿠폰발급요청_생성(jsonPath.getString("eventName"),jsonPath.getLong("id"), 고객생성요청());

        ExtractableResponse<Response> response = 쿠폰발급요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void issuanceRequest_수량초과() {
        int stock = 1;
        JsonPath jsonPath = 쿠폰이벤트생성(쿠폰이벤트생성_요청(stock)).body().jsonPath();
        String couponEventName = jsonPath.getString("eventName");
        long couponEventId = jsonPath.getLong("id");
        CouponIssuanceRequest request = 쿠폰발급요청_생성(couponEventName,couponEventId, 고객생성요청());

        ExtractableResponse<Response> response1 = 쿠폰발급요청(request);

        assertThat(response1.statusCode()).isEqualTo(HttpStatus.OK.value());

        CouponIssuanceRequest request2 = 쿠폰발급요청_생성(couponEventName,couponEventId, 고객생성요청());
        ExtractableResponse<Response> response2 = 쿠폰발급요청(request2);
        System.out.println(response2.response().body());
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

    }

    @Test
    void issuanceRequest_중복참여() {
        int stock = 1;
        JsonPath jsonPath = 쿠폰이벤트생성(쿠폰이벤트생성_요청(stock)).body().jsonPath();
        String couponEventName = jsonPath.getString("eventName");
        long couponEventId = jsonPath.getLong("id");
        CouponIssuanceRequest request = 쿠폰발급요청_생성(couponEventName,couponEventId, 고객생성요청());

        ExtractableResponse<Response> response1 = 쿠폰발급요청(request);
        assertThat(response1.statusCode()).isEqualTo(HttpStatus.OK.value());

        CouponIssuanceRequest request2 = 쿠폰발급요청_생성(couponEventName,couponEventId, 고객생성요청());
        ExtractableResponse<Response> response2 = 쿠폰발급요청(request2);
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

    }

//    @Test
    void issuanceRequest_부하() {
        int stock = 10;
        JsonPath jsonPath = 쿠폰이벤트생성(쿠폰이벤트생성_요청(stock)).body().jsonPath();
        String couponEventName = jsonPath.getString("eventName");
        long couponEventId = jsonPath.getLong("id");
        int users = 10;
        ExecutorService es = Executors.newFixedThreadPool(users);
        for (int i=0; i<users; i++){
            es.submit(() -> {
                쿠폰발급요청(쿠폰발급요청_생성(couponEventName, couponEventId, 고객생성요청()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        ExtractableResponse<Response> response = 쿠폰이벤트조회(couponEventId);
        int quantity = response.body().jsonPath().getInt("quantity");

        Assertions.assertThat(quantity).isEqualTo(0);

    }
}