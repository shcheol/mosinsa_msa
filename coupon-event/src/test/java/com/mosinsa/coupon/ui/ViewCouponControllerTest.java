package com.mosinsa.coupon.ui;

import com.mosinsa.ControllerTest;
import com.mosinsa.common.exception.CouponError;
import com.mosinsa.coupon.query.application.CouponQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ViewCouponControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CouponQueryService couponQueryService;

    @Test
    void couponDetails() throws Exception {

        String couponId = "id";
        mockMvc.perform(get("/coupons/" + couponId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("couponId").value(couponId));

    }

    @Test
    void myCoupons() throws Exception {

        String memberId = "memberId";

        mockMvc.perform(get("/coupons/my")
                        .header("customer-info",
                                "\"{\"name\":\"name\",\"id\":\"" + memberId + "\"}\"")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void globalException() throws Exception {
        String couponId = "globalEx";
        mockMvc.perform(get("/coupons/" + couponId))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    void globalProductException5xx() throws Exception {
        String couponId = "id5xx";
        mockMvc.perform(get("/coupons/" + couponId))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("result").value("error"))
                .andExpect(jsonPath("message").value(CouponError.INTERNAL_SERVER_ERROR.getMessage()))
                .andDo(print());
    }

    @Test
    void globalProductException4xx() throws Exception {
        String couponId = "id4xx";
        mockMvc.perform(get("/coupons/" + couponId))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("result").value("error"))
                .andExpect(jsonPath("message").value(CouponError.NOT_FOUND.getMessage()))
                .andDo(print());
    }

}