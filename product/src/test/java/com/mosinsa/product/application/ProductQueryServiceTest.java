package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDetailDto;
import com.mosinsa.product.application.dto.ProductQueryDto;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Test
    void findByCondition(){
        SearchCondition searchCondition = new SearchCondition("categoryId1");
        Page<ProductQueryDto> byCondition = productQueryService.findProductsByCondition(searchCondition, PageRequest.of(0,3));
        int size = byCondition.getContent().size();
        assertThat(size).isEqualTo(3);

        List<ProductQueryDto> content = byCondition.getContent();

        int idx=0;
        List<String> answers = List.of("productId4", "productId1", "productId5");
        for (ProductQueryDto productQueryDto : content) {
            assertThat(productQueryDto.getProductId()).isEqualTo(answers.get(idx++));
        }
    }

}