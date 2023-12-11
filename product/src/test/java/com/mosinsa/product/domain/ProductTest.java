package com.mosinsa.product.domain;

import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.domain.product.InvalidMoneyException;
import com.mosinsa.product.domain.product.InvalidStockException;
import com.mosinsa.product.domain.product.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void invalidMoneyException(){

		assertThrows(InvalidMoneyException.class, () -> Product.create("name", 0, Category.of("category1"), 10));
    }

	@Test
	void invalidStockException(){

		assertThrows(InvalidStockException.class, () -> Product.create("name", 100, Category.of("category1"), 0));
	}

}