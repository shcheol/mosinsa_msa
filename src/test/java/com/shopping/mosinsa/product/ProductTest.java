package com.shopping.mosinsa.product;

import com.shopping.mosinsa.controller.request.ProductUpdateRequest;
import com.shopping.mosinsa.entity.DiscountPolicy;
import com.shopping.mosinsa.entity.Product;
import com.shopping.mosinsa.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ProductTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ProductRepository productRepository;
    Product savedProduct;

    @BeforeEach
    void setup(){
        Product productA = new Product("productA", 1000, 10, DiscountPolicy.NONE);
        savedProduct = productRepository.save(productA);

        em.flush();
        em.clear();
    }

    @Test
    void 상품등록() {

        Product productA = new Product("productA", 1000, 10, DiscountPolicy.NONE);
        Product savedProduct = productRepository.save(productA);

        assertThat(productA).isEqualTo(savedProduct);
    }


    @Test
    void 상품조회_하나() {

        Product findProduct = productRepository.findById(savedProduct.getId()).get();
        assertThat(findProduct.getName()).isEqualTo(savedProduct.getName());
        assertThat(findProduct.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(findProduct.getStock()).isEqualTo(savedProduct.getStock());
        assertThat(findProduct.getDiscountPolicy()).isEqualTo(savedProduct.getDiscountPolicy());
    }


    @Test
    void 상품수정() {

        Product findProduct = productRepository.findById(savedProduct.getId()).get();

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest("상품수정", 2000, 20, DiscountPolicy.TEN_PERCENTAGE, 0);
        findProduct.change(productUpdateRequest);

        em.flush();
        em.clear();

        Product updateProduct = productRepository.findById(savedProduct.getId()).get();

        assertThat(updateProduct.getName()).isEqualTo("상품수정");
        assertThat(updateProduct.getPrice()).isEqualTo(2000);
        assertThat(updateProduct.getStock()).isEqualTo(20);
        assertThat(updateProduct.getDiscountPolicy()).isEqualTo(DiscountPolicy.TEN_PERCENTAGE);
    }

    @Test
    void 할인가격조회_none(){
        Product product = new Product("product", 1000, 10, DiscountPolicy.NONE);
        Product save = productRepository.save(product);

        assertThat(save.getDiscountPrice()).isEqualTo(0);
    }

    @Test
    void 할인가격조회_10퍼센트(){
        Product product = new Product("product", 1000, 10, DiscountPolicy.TEN_PERCENTAGE);
        Product save = productRepository.save(product);

        assertThat(save.getDiscountPrice()).isEqualTo(100);
    }

    @Test
    void addStock(){
        Product product = new Product("product", 1000, 10, DiscountPolicy.TEN_PERCENTAGE);
        Product save = productRepository.save(product);

        save.addStock(13);
        assertThat(save.getStock()).isEqualTo(23);
    }
    @Test
    void removeStock_정상(){
        Product product = new Product("product", 1000, 10, DiscountPolicy.TEN_PERCENTAGE);
        Product save = productRepository.save(product);

        save.removeStock(3);
        assertThat(save.getStock()).isEqualTo(7);
    }

    @Test
    void removeStock_에러(){
        Product product = new Product("product", 1000, 10, DiscountPolicy.TEN_PERCENTAGE);
        Product save = productRepository.save(product);

        assertThatThrownBy(() -> save.removeStock(13))
                .isInstanceOf(RuntimeException.class);
    }

}