package com.mosinsa.promotion.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PromotionControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("promotion 참여")
    void participate() throws Exception {

        mockMvc.perform(post("/promotions/promotionId1")
                        .header("customer-info", """
                                "{"name":"name","id":"id"}"
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                    {
                                    "quests":[{"id":1}]
                                    }
                                """)
                ).andExpect(status().isOk())
                .andDo(print());

    }
}