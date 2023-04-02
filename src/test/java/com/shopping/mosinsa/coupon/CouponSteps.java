package com.shopping.mosinsa.coupon;

import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.entity.DiscountPolicy;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

public class CouponSteps {
    public static CouponEventCreateRequest 쿠폰이벤트생성_요청(int stock) {
        CouponEventCreateRequest request = new CouponEventCreateRequest("10주년이벤트",
                DiscountPolicy.TEN_PERCENTAGE, stock,
                LocalDateTime.now().minusMinutes(1),
                LocalDateTime.of(2024, 3,26,12, 0));
        return request;
    }
    public static ExtractableResponse<Response> 쿠폰이벤트생성(CouponEventCreateRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/coupon/event")
                .then()
                .log().all().extract();
    }

    public static CouponIssuanceRequest 쿠폰발급요청_생성(Long customerId, Long couponEventId) {
        return new CouponIssuanceRequest(customerId, couponEventId);
    }
}
