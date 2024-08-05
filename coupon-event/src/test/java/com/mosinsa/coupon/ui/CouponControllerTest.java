package com.mosinsa.coupon.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.application.CouponServiceImpl;
import com.mosinsa.coupon.command.domain.Coupon;
import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.coupon.query.application.dto.CouponDto;
import com.mosinsa.coupon.ui.response.CouponResponse;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.application.dto.JoinPromotionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CouponController.class)
@Import(CouponPresentationObjectFactory.class)
class CouponControllerTest {

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