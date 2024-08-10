package com.mosinsa.promotion.ui;

import com.mosinsa.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PromotionControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("promotion 생성")
    void create() throws Exception {

        mockMvc.perform(post("/promotions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                    {
                                    "title":"제목",
                                    "context":"내용",
                                    "quantity" : 20,
                                    "discountPolicy":"TEN_PERCENTAGE",
                                    "period":{
                                        "startDate":"2023-11-06T00:00:00",
                                        "endDate":"2024-11-06T00:00:00"
                                    },
                                    "details":{
                                            "discountPolicy":"TEN_PERCENTAGE",
                                            "duringDate":"2024-11-06T00:00:00"
                                    }
                                    }
                                """)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("testId"))
                .andDo(print());

    }
}