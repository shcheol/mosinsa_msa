package com.mosinsa.coupon.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CouponControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("쿠폰 발급")
    void joinPromotionsWithLogin() throws Exception {
        mockMvc.perform(
                        post("/coupons/issue")
                                .header("customer-info", """
                                        "{"name":"name","id":"id"}"
                                        """)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                        "promotionId":"promotionId"
                                        }
                                        """)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 사용")
    void useCoupon() throws Exception {
        mockMvc.perform(
                        post("/coupons/couponId")
                                .header("customer-info", """
                                        "{"name":"name","id":"id"}"
                                        """)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                        "promotionId":"promotionId"
                                        }
                                        """)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("쿠폰 사용 취소")
    void useCouponCancel() throws Exception {
        mockMvc.perform(
                        post("/coupons/couponId/cancel")
                                .header("customer-info", """
                                        "{"name":"name","id":"id"}"
                                        """)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                        "promotionId":"promotionId"
                                        }
                                        """)
                ).andExpect(status().isOk())
                .andDo(print());
    }

}