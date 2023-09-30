package com.mosinsa.product.product;

import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.db.entity.DiscountPolicy;

public class ProductSteps {

    public static ProductAddRequest 상품등록요청_생성(){
        return new ProductAddRequest("상품1", 1000, 10, DiscountPolicy.NONE);
    }
    public static ProductUpdateRequest 상품수정요청_생성() {
        return new ProductUpdateRequest("상품수정", 2000, 20, DiscountPolicy.TEN_PERCENTAGE, 1);
    }
/*    public static ExtractableResponse<Response> 상품등록요청(ProductAddRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all().extract();
    }



    public static ExtractableResponse<Response> 상품수정요청(String productId, ProductUpdateRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/products/"+productId)
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 상품상세요청(String id) {
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
    }*/

}
