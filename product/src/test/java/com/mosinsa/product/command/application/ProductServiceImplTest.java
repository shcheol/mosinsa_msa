package com.mosinsa.product.command.application;

import com.mosinsa.common.ex.CategoryException;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.domain.InvalidStockException;
import com.mosinsa.product.command.domain.StockStatus;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.query.ProductQueryService;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
@Import(ProductApplicationObjectFactory.class)
class ProductServiceImplTest {

    @Autowired
    ProductQueryService productQueryService;
    @Autowired
    ProductService productService;

	@Autowired
	StockPort stockPort;

	@BeforeEach
	void setup(){
		StockPortStub stockPort1 = (StockPortStub) stockPort;
		stockPort1.init();
	}

    @Test
    @DisplayName(value = "상품등록")
    void registerProduct() {
        CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 10);
        ProductDetailDto product = productService.createProduct(createProductRequest);
        assertThat(product.getName()).isEqualTo("product");
        assertThat(product.getPrice()).isEqualTo(3000);
        assertThat(product.getTotalStock()).isEqualTo(10);
    }

    @Test
    @DisplayName(value = "상품등록실패 - 카테고리 존재x")
    void registerProductNotExistsCategory() {
        CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "notFound", 10);
        assertThrows(CategoryException.class, () -> productService.createProduct(createProductRequest));
    }

    @Test
    @DisplayName(value = "솔드아웃된 상품을 주문시도하면 실패한다.")
    void orderAlreadySoldOutProduct() {

        OrderProductRequest request = new OrderProductRequest("productId2", 3);
        List<OrderProductRequest> list = List.of(request);
        assertThrows(AlreadySoldOutException.class,
                () -> productService.orderProduct("customerId1", "orderId1", list));
    }

    @Test
    @DisplayName(value = "재고가 0이된 상품은 상태가 SOLD_OUT으로 변경된다.")
    void orderProductSoldOut() {

        String productId1 = "productId1";
        String productId4 = "productId4";
        OrderProductRequest request = new OrderProductRequest(productId1, 10);
        OrderProductRequest request2 = new OrderProductRequest(productId4, 5);
        productService.orderProduct("customerId1", "orderId1", List.of(request, request2));

        assertThat(productQueryService.getProductById(productId1).getStockStatus()).isEqualTo(StockStatus.SOLD_OUT);
        assertThat(productQueryService.getProductById(productId4).getStockStatus()).isEqualTo(StockStatus.ON);
    }


    @Test
    @DisplayName(value = "재고가 부족하면 예외 발생")
    void orderProductNoEnoughStock() {

        String productId = "productId1";

        List<OrderProductRequest> products = List.of(new OrderProductRequest(productId, 11));
        assertThrows(InvalidStockException.class,
                () -> productService.orderProduct("customerId1", "fail", products));
    }

    @Test
    @DisplayName(value = "재고 증가 실패하면 예외 발생")
    void cancelOrderProduct() {
        String productId = "productId1";

        CancelOrderProductRequest request = new CancelOrderProductRequest(productId, 3);
        List<CancelOrderProductRequest> list = List.of(request);
        assertThrows(InvalidStockException.class,
                () -> productService.cancelOrderProduct("customerId1", "fail", list));
    }

    @Test
    @DisplayName(value = "재고 증가 실패하면 예외 발생")
    void cancelOrderInvalidProductId() {
        String productId = "productIdxxxx";

        CancelOrderProductRequest request = new CancelOrderProductRequest(productId, 3);
        List<CancelOrderProductRequest> list = List.of(request);
        assertThrows(ProductException.class,
                () -> productService.cancelOrderProduct("customerId1", "orderId", list));
    }

    @Test
    @DisplayName(value = "잔여 수량이 0인 상품의 수량이 올라가면 재고 상태 ON으로 변경된다.")
    void cancelOrderProductWhenStockIsZero() {
        String productId2 = "productId2";
        String productId4 = "productId4";
        assertThat(productQueryService.getProductById(productId2).getStockStatus()).isEqualTo(StockStatus.SOLD_OUT);
        assertThat(productQueryService.getProductById(productId4).getStockStatus()).isEqualTo(StockStatus.ON);

        productService.cancelOrderProduct("customerId1", "orderId1",
                List.of(new CancelOrderProductRequest(productId2, 10)));
        assertThat(productQueryService.getProductById(productId2).getStockStatus()).isEqualTo(StockStatus.ON);
        assertThat(productQueryService.getProductById(productId4).getStockStatus()).isEqualTo(StockStatus.ON);
    }

    @Test
    @DisplayName(value = "취소 수량이 0이하면 상태가 변경되지 않음")
    void cancelOrderProductWhenInvalidValue() {
        String productId2 = "productId2";
        assertThat(productQueryService.getProductById(productId2).getStockStatus()).isEqualTo(StockStatus.SOLD_OUT);
		String productId3 = "productId3";
		assertThat(productQueryService.getProductById(productId3).getStockStatus()).isEqualTo(StockStatus.SOLD_OUT);
		String productId4 = "productId4";
		assertThat(productQueryService.getProductById(productId4).getStockStatus()).isEqualTo(StockStatus.ON);
		String productId5 = "productId5";
		assertThat(productQueryService.getProductById(productId5).getStockStatus()).isEqualTo(StockStatus.ON);

        productService.cancelOrderProduct("customerId1", "orderId1",
                List.of(
						new CancelOrderProductRequest(productId2, 2),
						new CancelOrderProductRequest(productId3, 0),
						new CancelOrderProductRequest(productId4, 3),
						new CancelOrderProductRequest(productId4, 0)

						));
        assertThat(productQueryService.getProductById(productId2).getStockStatus()).isEqualTo(StockStatus.ON);
		assertThat(productQueryService.getProductById(productId3).getStockStatus()).isEqualTo(StockStatus.SOLD_OUT);
		assertThat(productQueryService.getProductById(productId4).getStockStatus()).isEqualTo(StockStatus.ON);
        assertThat(productQueryService.getProductById(productId5).getStockStatus()).isEqualTo(StockStatus.ON);
    }


}