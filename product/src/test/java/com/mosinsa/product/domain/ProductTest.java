package com.mosinsa.product.domain;

import com.mosinsa.product.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTest {

    @Test
    void newProduct(){
        Product product = Product.create("name", 1000, "category1", 10);

    }

}