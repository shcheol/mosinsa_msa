package com.mosinsa.order.infra.api.feignclient.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.NotEnoughProductStockException;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductFeignAdapterTest {

    @MockBean
    ProductClient client;

    @Autowired
    ProductFeignAdapter adapter;

    @Test
    void confirm() throws JsonProcessingException {
        when(client.getProduct(any(), any()))
                .thenReturn(
                        new ProductResponse("productId", "", 1000, 3, 3000, ""));

        List<OrderProductDto> confirm = adapter.confirm(getOrderConfirmRequest());
        Assertions.assertThat(confirm).hasSize(1);
    }

    @Test
    void confirmEx() throws JsonProcessingException {
        when(client.getProduct(any(), any()))
                .thenReturn(
                        new ProductResponse("productId", "", 1000, 1, 3000, ""));
        OrderConfirmRequest orderConfirmRequest = getOrderConfirmRequest();
        assertThrows(NotEnoughProductStockException.class, () ->  adapter.confirm(orderConfirmRequest));
    }

    @Test
    void orderProducts() throws JsonProcessingException {
        doNothing().when(client).orderProducts(any(), any());

        ResponseResult<Void> response = adapter.orderProducts("", getOrderConfirm());
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
    }

    OrderConfirmRequest getOrderConfirmRequest() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String s = """
                {
                	"couponId":"couponId",
                	"myOrderProducts":[
                		{
                			"productId":"productId",
                			"quantity":2
                		}
                		],
                		"shippingInfo":{
                			"message":"home",
                			"address":{
                				"zipCode":"zipcode",
                				"address1":"address1",
                				"address2":"address2"
                			},
                			"receiver":{
                				"name":"myname",
                				"phoneNumber":"010-1111-1111"
                			}
                		}
                }
                """;
        return om.readValue(s, OrderConfirmRequest.class);
    }

    OrderConfirmDto getOrderConfirm() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String s = """
                {
                				         "couponId": "couponId",
                				         "customerId": "customerId",
                				         "orderProducts": [
                				             {
                				                 "productId": "productId",
                				                 "price": 3000,
                				                 "quantity": 2,
                				                 "amounts": 6000
                				             }
                				         ],
                				         "shippingInfo": {
                				             "message": "home",
                				             "address": {
                				                 "zipCode": "zipcode",
                				                 "address1": "address1",
                				                 "address2": "address2"
                				             },
                				             "receiver": {
                				                 "name": "myname",
                				                 "phoneNumber": "010-1111-1111"
                				             }
                				         },
                				         "totalAmount": 5400
                				     }
                 				 
                				""";
        return om.readValue(s, OrderConfirmDto.class);
    }
}