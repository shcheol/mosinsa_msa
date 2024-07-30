package com.mosinsa.promotion.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ViewPromotionControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("promotion 목록 조회")
    void promotionList() throws Exception {

        mockMvc.perform(get("/promotions"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("promotion 상세 조회")
    void promotionNoLogin() throws Exception {


        String promotionId = "promotionId";
        mockMvc.perform(
                        get("/promotions/" + promotionId)
                ).andExpect(status().isOk())
                .andDo(print());
    }

}