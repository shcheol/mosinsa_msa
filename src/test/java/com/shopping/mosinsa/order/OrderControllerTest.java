package com.shopping.mosinsa.order;

import com.shopping.mosinsa.ApiTest;
import com.shopping.mosinsa.controller.request.OrderCreateRequest;
import com.shopping.mosinsa.entity.Customer;
import com.shopping.mosinsa.entity.CustomerGrade;
import com.shopping.mosinsa.repository.CustomerRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.shopping.mosinsa.order.OrderSteps.상품주문;
import static com.shopping.mosinsa.order.OrderSteps.상품주문요청_생성;
import static com.shopping.mosinsa.product.ProductSteps.상품등록요청;
import static com.shopping.mosinsa.product.ProductSteps.상품등록요청_생성;
import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerTest extends ApiTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void 회원주문목록(){

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/orders")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 주문목록(){

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/orders")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 주문하기(){
        Customer customer = new Customer(CustomerGrade.BRONZE);
        Customer savedCustomer = customerRepository.save(customer);

        Long id1 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
        Long id2 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
        ExtractableResponse<Response> response = 상품주문(savedCustomer.getId(), 상품주문요청_생성(id1, id2));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }



    @Test
    void 주문취소(){
        Customer customer = new Customer(CustomerGrade.BRONZE);
        Customer savedCustomer = customerRepository.save(customer);

        Long id1 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
        Long id2 = 상품등록요청(상품등록요청_생성()).body().jsonPath().getLong("id");
        ExtractableResponse<Response> response = 상품주문(savedCustomer.getId(), 상품주문요청_생성(id1, id2));

        long id = response.body().jsonPath().getLong("id");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch("/orders/"+customer.getId()+"?orderId="+id)
                .then().log().all().extract();

    }


}