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
		Category of = Category.of("category1");
		assertThrows(InvalidMoneyException.class, () -> Product.create("name", 0, of, 10));
    }

	@Test
	void invalidStockException(){
		Category of = Category.of("category1");
		assertThrows(InvalidStockException.class, () -> Product.create("name", 100, of, 0));
	}

}