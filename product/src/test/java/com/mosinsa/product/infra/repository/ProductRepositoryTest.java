package com.mosinsa.product.infra.repository;

import com.mosinsa.product.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    @Commit
    void create(){
        Product product = Product.create("name", 1000, "category1", 10);
        Product saveProduct = repository.save(product);

        assertThat(product).isEqualTo(saveProduct);
        assertThat(product.getId()).isEqualTo(saveProduct.getId());
        assertThat(product.getName()).isEqualTo(saveProduct.getName());
        assertThat(product.getCategory()).isEqualTo(saveProduct.getCategory());
        assertThat(product.getStock()).isEqualTo(saveProduct.getStock());
        assertThat(product.totalLikes()).isZero();
        assertThat(product.getLikes()).isEmpty();
    }

}