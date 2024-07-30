package com.mosinsa;

import com.mosinsa.code.TestController;
import com.mosinsa.coupon.ui.CouponController;
import com.mosinsa.coupon.ui.CouponPresentationObjectFactory;
import com.mosinsa.coupon.ui.ViewCouponController;
import com.mosinsa.promotion.ui.PromotionController;
import com.mosinsa.promotion.ui.PromotionPresentationObjectFactory;
import com.mosinsa.promotion.ui.ViewPromotionController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest({
        PromotionController.class,
        ViewPromotionController.class,
        CouponController.class,
        TestController.class,
        ViewCouponController.class,

})
@Import({
        PromotionPresentationObjectFactory.class,
        CouponPresentationObjectFactory.class,

})
public abstract class ControllerTest {
}