package com.mosinsa.product.product;

import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.dto.ProductDto;
import com.mosinsa.product.entity.DiscountPolicy;
import com.mosinsa.product.entity.Product;
import com.mosinsa.product.repository.ProductRepository;
import com.mosinsa.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static com.mosinsa.product.product.ProductSteps.상품등록요청_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Test
    void 상품등록(){
        ProductDto saveProduct = productService.addProduct(상품등록요청_생성());

        ProductDto product = productService.getProduct(saveProduct.getProductId());

        assertThat(product.getName()).isEqualTo("상품1");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getStock()).isEqualTo(10);
        assertThat(product.getDiscountPolicy()).isEqualTo(DiscountPolicy.NONE);
        assertThat(product.getDiscountPrice()).isEqualTo(0);

    }

    @Test
    void 상품수정(){

        Product product = productRepository.save(new Product("상품1", 1000, 10, DiscountPolicy.NONE));
        Product findProduct = productRepository.findById(product.getId()).get();

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest("상품수정", 2000, 20, DiscountPolicy.TEN_PERCENTAGE, 0);
        productService.updateProduct(findProduct.getId(), productUpdateRequest);

        Product updateProduct = productRepository.findById(findProduct.getId()).get();

        assertThat(updateProduct.getName()).isEqualTo("상품수정");
        assertThat(updateProduct.getPrice()).isEqualTo(2000);
        assertThat(updateProduct.getStock()).isEqualTo(20);
        assertThat(updateProduct.getDiscountPolicy()).isEqualTo(DiscountPolicy.TEN_PERCENTAGE);
    }

    @Test
    void 상품수정_예외(){

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest("상품수정", 2000, 20, DiscountPolicy.TEN_PERCENTAGE, 0);

        assertThatThrownBy(() -> productService.updateProduct("productId1", productUpdateRequest)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 상품조회_상세(){
        ProductDto saveProduct = productService.addProduct(상품등록요청_생성());

        ProductDto product = productService.getProduct(saveProduct.getProductId());

        assertThat(product.getName()).isEqualTo("상품1");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getStock()).isEqualTo(10);
        assertThat(product.getDiscountPolicy()).isEqualTo(DiscountPolicy.NONE);
        assertThat(product.getDiscountPrice()).isEqualTo(0);
    }

    @Test
    void 상품조회_목록(){

        for (int i=1; i<=10; i++){
            Product product = new Product("product" + i, 1000*i, 10*i, DiscountPolicy.NONE);
            productRepository.save(product);
        }

        PageRequest of = PageRequest.of(1, 3);
        Page<ProductDto> products = productService.getProducts(of);

        assertThat(products.getSize()).isEqualTo(3);
        assertThat(products.getTotalElements()).isEqualTo(10);
        assertThat(products.getContent().get(0).getName()).isEqualTo("product4");
        assertThat(products.getContent().get(0).getPrice()).isEqualTo(4000);
        assertThat(products.getContent().get(0).getStock()).isEqualTo(40);
        assertThat(products.getContent().get(0).getDiscountPolicy()).isEqualTo(DiscountPolicy.NONE);
        assertThat(products.getContent().get(0).getDiscountPrice()).isEqualTo(0);

    }

}