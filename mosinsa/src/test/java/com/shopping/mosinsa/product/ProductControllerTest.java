package com.shopping.mosinsa.product;

import com.shopping.mosinsa.ApiTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static com.shopping.mosinsa.product.ProductSteps.*;
import static org.assertj.core.api.Assertions.assertThat;


@Transactional
class ProductControllerTest extends ApiTest {

    @Test
    public void 상품등록(){
        ExtractableResponse<Response> response = 상품등록요청(상품등록요청_생성());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void 상품수정(){
        상품등록요청(상품등록요청_생성());

        ExtractableResponse<Response> response = 상품수정요청(상품수정요청_생성());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void 상품목록(){
        for (int i=0;i<5;i++) {
            상품등록요청(상품등록요청_생성());
        }

        ExtractableResponse<Response> response = 상품목록요청();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getInt("totalElements")).isEqualTo(5);
    }



    @Test
    public void 상품상세(){
        ExtractableResponse<Response> addResponse = 상품등록요청(상품등록요청_생성());

        Long id = addResponse.jsonPath().getLong("id");

        ExtractableResponse<Response> response = 상품상세요청(id);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("상품1");
    }




}