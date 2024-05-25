package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDetailDto;
import com.mosinsa.product.common.ex.ProductException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ProductQueryServiceTest {

    @Autowired
    ProductQueryService productQueryService;

    @Test
    void getProductEx() {
        String productId = "productId1xxx";

        assertThrows(ProductException.class, () ->
                productQueryService.getProductById(productId));
    }

    @Test
    void findMyLikesProduct(){
        List<ProductDetailDto> myLikesProducts = productQueryService.findMyLikesProducts("customerId1");
        assertThat(myLikesProducts).hasSize(2);
        List<String> ids = myLikesProducts.stream().map(ProductDetailDto::getProductId).toList();
        assertThat(ids).containsExactly("productId4","productId5");
    }

}