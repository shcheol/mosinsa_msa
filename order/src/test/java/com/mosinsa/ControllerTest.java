package com.mosinsa;

import com.mosinsa.order.code.TestController;
import com.mosinsa.order.ui.OrderApplicationObjectFactory;
import com.mosinsa.order.ui.OrderController;
import com.mosinsa.order.ui.ViewOrderController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest({
		ViewOrderController.class,
		OrderController.class,
		TestController.class,
})
@Import({
		OrderApplicationObjectFactory.class,
})
public abstract class ControllerTest {
}