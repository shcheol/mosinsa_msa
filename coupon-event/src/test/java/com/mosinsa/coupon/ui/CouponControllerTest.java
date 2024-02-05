package com.mosinsa.coupon.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.coupon.domain.Coupon;
import com.mosinsa.coupon.domain.CouponDetails;
import com.mosinsa.coupon.domain.DiscountPolicy;
import com.mosinsa.coupon.dto.CouponDto;
import com.mosinsa.coupon.ui.response.CouponResponse;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.dto.JoinPromotionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	CouponService couponService;

	ObjectMapper om = new ObjectMapper();

	MockHttpSession session;

	@BeforeEach
	void setUp() {
		session = new MockHttpSession();
	}

	@AfterEach
	void clean() {
		session.clearAttributes();
	}

	@Test
	void couponDetails() throws Exception {

		Coupon coupon = Coupon.create(
				PromotionId.newId(), new CouponDetails(LocalDateTime.now(), DiscountPolicy.TEN_PERCENTAGE)
		);
		coupon.issuedCoupon("memberId");
		CouponDto couponDto = CouponDto.convert(coupon);
		CouponResponse couponResponse = new CouponResponse(couponDto);

		when(couponService.findById(any()))
				.thenReturn(couponDto);

		mockMvc.perform(get("/coupons/" + couponDto.getCouponId())
				)
//                .andExpect(model().attribute("coupon",couponDto))
//                .andExpect(view().name("my/couponDetail"));
				.andExpect(status().isOk());

		verify(couponService).findById(any());

	}

	@Test
	void myCoupons() throws Exception {

		String memberId = "memberId";

		Coupon coupon1 = Coupon.create(
				PromotionId.newId(), new CouponDetails(LocalDateTime.now(), DiscountPolicy.TEN_PERCENTAGE));
		coupon1.issuedCoupon(memberId);
		Coupon coupon2 = Coupon.create(
				PromotionId.newId(), new CouponDetails(LocalDateTime.now(), DiscountPolicy.TEN_PERCENTAGE));
		coupon2.issuedCoupon(memberId);

		CouponDto couponDto1 = CouponDto.convert(coupon1);
		CouponDto couponDto2 = CouponDto.convert(coupon2);

		List<CouponDto> couponDtos = List.of(couponDto1, couponDto2);
		when(couponService.myCoupons(memberId))
				.thenReturn(couponDtos);

		mockMvc.perform(get("/coupons/my/" + memberId)
						.session(session))
//                .andExpect(model().attribute("coupons",couponDtos))
//                .andExpect(view().name("my/coupons"));
				.andExpect(status().isOk());

	}


	@Test
	@DisplayName("쿠폰 발급 - 로그인 안한 멤버")
	void joinPromotionsWithoutLogin() throws Exception {
		JoinPromotionRequest request = new JoinPromotionRequest(null, "promotion2");
		mockMvc.perform(
						patch("/coupons")
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(om.writeValueAsString(request))
				).andExpect(status().isBadRequest())
				.andExpect(jsonPath("result").value("login first"))
				.andDo(print());
	}

	@Test
	@DisplayName("쿠폰 발급 - 로그인 멤버")
	void joinPromotionsWithLogin() throws Exception {
		JoinPromotionRequest request = new JoinPromotionRequest("1", "promotion2");
		mockMvc.perform(
						patch("/coupons")
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(om.writeValueAsString(request))
				).andExpect(status().isOk())
				.andExpect(jsonPath("result").value("발급되었습니다."))
				.andDo(print());
	}

	@Test
	@DisplayName("쿠폰 발급 - 예외발생")
	void joinPromotionsDuplicateRequest() throws Exception {
		JoinPromotionRequest request = new JoinPromotionRequest("1", "promotion3");

		when(couponService.issue(any()))
				.thenThrow(CouponException.class);

		mockMvc.perform(
				patch("/coupons")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(om.writeValueAsString(request))
		).andExpect(status().isBadRequest());

		verify(couponService).issue(any());

	}
}