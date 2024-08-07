package com.mosinsa;

import com.mosinsa.category.CategoryController;
import com.mosinsa.category.CategoryObjectFactory;
import com.mosinsa.code.TestController;
import com.mosinsa.product.ui.ProductController;
import com.mosinsa.product.ui.ProductPresentationObjectFactory;
import com.mosinsa.product.ui.ViewProductController;
import com.mosinsa.reaction.ui.ReactionController;
import com.mosinsa.reaction.ui.ReactionPresentationObjectFactory;
import com.mosinsa.reaction.ui.ViewReactionController;
import com.mosinsa.review.ui.ReviewController;
import com.mosinsa.review.ui.ReviewPresentationObjectFactory;
import com.mosinsa.review.ui.ViewReviewController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest({
		ProductController.class,
		ViewProductController.class,
		ReactionController.class,
		ViewReactionController.class,
		ReviewController.class,
		ViewReviewController.class,
		TestController.class,
		CategoryController.class
})
@Import({
		ProductPresentationObjectFactory.class,
		ReactionPresentationObjectFactory.class,
		ReviewPresentationObjectFactory.class,
		CategoryObjectFactory.class
})
public abstract class ControllerTest {
}