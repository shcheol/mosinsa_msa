package com.mosinsa.promotion.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mosinsa.common.event.EventsConfig;
import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.coupon.domain.CouponDetails;
import com.mosinsa.coupon.domain.DiscountPolicy;
import com.mosinsa.promotion.application.PromotionService;
import com.mosinsa.promotion.domain.Promotion;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.domain.PromotionPeriod;
import com.mosinsa.promotion.dto.CreatePromotionRequest;
import com.mosinsa.promotion.dto.JoinPromotionRequest;
import com.mosinsa.promotion.dto.PromotionDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromotionController.class)
@ExtendWith(MockitoExtension.class)
@Import({EventsConfig.class})
class PromotionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PromotionService promotionService;

    @MockBean
    CouponService couponService;

    LocalDateTime before = LocalDateTime.of(2023, 10, 28, 00, 00);
    LocalDateTime after = LocalDateTime.of(2024, 10, 28, 00, 00);
    static ObjectMapper om;

    @BeforeAll
    static void init() {
        om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @DisplayName("promotion 목록 조회")
    void promotionList() throws Exception {

        PromotionDto dto1 = PromotionDto.convert(Promotion.create(
                "title", "context", 3, DiscountPolicy.TEN_PERCENTAGE,
                new PromotionPeriod(before, after),
                new CouponDetails(after, DiscountPolicy.TEN_PERCENTAGE))
        );
        PromotionDto dto2 = PromotionDto.convert(Promotion.create(
                "title", "context", 3, DiscountPolicy.TEN_PERCENTAGE,
                new PromotionPeriod(before, after),
                new CouponDetails(after, DiscountPolicy.TEN_PERCENTAGE))
        );

        PageImpl<PromotionDto> page = new PageImpl<>(List.of(dto1, dto2), PageRequest.of(0,2),2);

        when(promotionService.findByPromotions(any(), any()))
                .thenReturn(page);

        mockMvc.perform(
                        get("/promotions")
                ).andExpect(status().isOk())
                .andExpect(model().attribute("promotions",page))
                .andExpect(view().name("promotion/promotionList"))
                .andDo(print());
    }

    @Test
    @DisplayName("promotion 목록 조회 - 예외 발생")
    void promotionListEx() throws Exception {

        when(promotionService.findByPromotions(any(), any()))
                .thenThrow(new CouponException(CouponError.NOT_FOUND));

        mockMvc.perform(
                        get("/promotions")
                ).andExpect(status().isOk())
                .andExpect(view().name("promotion/noPromotion"))
                .andDo(print());
    }

    @Test
    @DisplayName("promotion id로 조회")
    void promotionNoLogin() throws Exception {

        PromotionDto dto = PromotionDto.convert(Promotion.create(
                "title", "context", 3, DiscountPolicy.TEN_PERCENTAGE,
                new PromotionPeriod(before, after),
                new CouponDetails(after, DiscountPolicy.TEN_PERCENTAGE))
        );

        when(promotionService.findByPromotionId(any()))
                .thenReturn(dto);
        long stock = 3;
        when(couponService.count(any()))
                .thenReturn(stock);

        mockMvc.perform(
                        get("/promotions/"+dto.getPromotionId())
                ).andExpect(status().isOk())
                .andExpect(model().attribute("promotion",dto))
                .andExpect(model().attribute("stock", stock))
                .andExpect(view().name("promotion/promotionDetail"))
                .andDo(print());
    }

    @Test
    @DisplayName("promotion id로 조회 로그인한 멤버")
    void promotionLogin() throws Exception {

        MockHttpSession session = new MockHttpSession();

        PromotionDto dto = PromotionDto.convert(Promotion.create(
                "title", "context", 3, DiscountPolicy.TEN_PERCENTAGE,
                new PromotionPeriod(before, after),
                new CouponDetails(after, DiscountPolicy.TEN_PERCENTAGE))
        );

        when(promotionService.findByPromotionId(any()))
                .thenReturn(dto);
        long stock = 3;
        when(couponService.count(any()))
                .thenReturn(stock);

        mockMvc.perform(
                        get("/promotions/"+dto.getPromotionId())
                                .session(session)
                ).andExpect(status().isOk())
                .andExpect(model().attribute("promotion",dto))
                .andExpect(model().attribute("stock", stock))
                .andExpect(view().name("promotion/promotionDetail"))
                .andDo(print());
    }

    @Test
    @DisplayName("promotion 참여 - 로그인 안한 멤버")
    void joinPromotionsWithoutLogin() throws Exception {
        JoinPromotionRequest request = new JoinPromotionRequest(null, "promotion1");
        mockMvc.perform(
                        patch("/promotions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(om.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("result").value("login first"))
                .andDo(print());
    }

    @Test
    @DisplayName("promotion 참여 - 로그인 멤버")
    void joinPromotionsWithLogin() throws Exception {
        JoinPromotionRequest request = new JoinPromotionRequest("1", "promotion1");
        mockMvc.perform(
                        patch("/promotions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(om.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("result").value("request..."))
                .andDo(print());
    }

    @Test
    @DisplayName("promotion 생성")
    void create() throws Exception {

        CreatePromotionRequest createPromotionRequest =
                new CreatePromotionRequest("title", "context", 500, DiscountPolicy.TEN_PERCENTAGE,
                        new PromotionPeriod(
                                LocalDateTime.of(2023, 10, 28, 00, 00),
                                LocalDateTime.of(2024, 10, 28, 00, 00)),
                        new CouponDetails(
                                LocalDateTime.of(2024, 10, 28, 00, 00),
                                DiscountPolicy.TEN_PERCENTAGE));

        when(promotionService.create(any()))
                .thenReturn(
                        new PromotionDto(
                                PromotionId.of("promotionTestId"),
                                "title",
                                "context",
                                500,
                                DiscountPolicy.TEN_PERCENTAGE,
                                new PromotionPeriod(
                                        LocalDateTime.of(2023, 10, 28, 00, 00),
                                        LocalDateTime.of(2024, 10, 28, 00, 00))
                        )
                );


        mockMvc.perform(
                        post("/promotions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(om.writeValueAsString(createPromotionRequest))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("promotionId").value("promotionTestId"))
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("context").value("context"))
                .andExpect(jsonPath("quantity").value("500"))
                .andExpect(jsonPath("discountPolicy").value("TEN_PERCENTAGE"))
                .andExpect(jsonPath("period.startDate").value("2023-10-28T00:00:00"))
                .andExpect(jsonPath("period.endDate").value("2024-10-28T00:00:00"));

        verify(promotionService).create(createPromotionRequest);

    }
}