package com.shopping.mosinsa.product;

import com.shopping.mosinsa.controller.request.ProductAddRequest;
import com.shopping.mosinsa.controller.request.ProductUpdateRequest;
import com.shopping.mosinsa.entity.DiscountPolicy;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class ProductSteps {

    public static ProductAddRequest 상품등록요청_생성(){
        return new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE);
    }

    public static ExtractableResponse<Response> 상품등록요청(ProductAddRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all().extract();
    }

    public static ProductUpdateRequest 상품수정요청_생성() {
        return new ProductUpdateRequest("상품수정", 2000, 20, DiscountPolicy.TEN_PERCENTAGE, 1);
    }

    public static ExtractableResponse<Response> 상품수정요청(ProductUpdateRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/products/1")
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 상품상세요청(Long id) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products/"+id)
                .then()
                .log().all().extract();
    }
    public static ExtractableResponse<Response> 상품목록요청() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .log().all().extract();
    }

}
