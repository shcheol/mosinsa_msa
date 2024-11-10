package com.mosinsa.order.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void order() throws Exception {
        mockMvc.perform(post("/orders/order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
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
                                """))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    void orderCancel() throws Exception {
        mockMvc.perform(post("/orders/orderId1/cancel"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void globalExceptionExternalServerException() throws Exception {
        mockMvc.perform(post("/orders/externalServerException/cancel"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }


    @Test
    void globalExceptionCommon() throws Exception {
        mockMvc.perform(post("/orders/exception/cancel"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }


}