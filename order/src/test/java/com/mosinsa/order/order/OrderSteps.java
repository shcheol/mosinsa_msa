package com.mosinsa.order.order;

import com.mosinsa.order.controller.request.OrderCancelRequest;
import com.mosinsa.order.controller.request.OrderCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class OrderSteps {


    public static OrderCreateRequest 상품주문요청_생성(Long customerId, Long... ids) {

        Map<Long, Integer> orderProductMap = new HashMap<>();
        orderProductMap.put(ids[0], 3);
        orderProductMap.put(ids[1], 5);
        return new OrderCreateRequest(customerId, orderProductMap);
    }

    public static ExtractableResponse<Response> 상품주문(OrderCreateRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/orders")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 주문취소요청(OrderCancelRequest orderCancelRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderCancelRequest)
                .when()
                .patch("/orders")
                .then().log().all().extract();
    }

    public static OrderCancelRequest 주문취소요청_생성(Long customerId, Long orderId) {
        return new OrderCancelRequest(customerId, orderId);
    }

    public static ExtractableResponse<Response> 주문조회요청(long orderId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/orders/" + orderId)
                .then().log().all().extract();
    }
}
