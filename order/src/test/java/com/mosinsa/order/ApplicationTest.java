package com.mosinsa.order;

import com.mosinsa.order.infra.api.feignclient.coupon.CouponClient;
import com.mosinsa.order.infra.api.feignclient.product.ProductClient;
import com.mosinsa.order.infra.stub.ExternalApiObjectFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
@Import({
		ExternalApiObjectFactory.class
})
@ExtendWith(MockitoExtension.class)
public abstract class ApplicationTest {


	@MockBean
	public CouponClient couponClient;


	@MockBean
	public ProductClient productClient;

}
