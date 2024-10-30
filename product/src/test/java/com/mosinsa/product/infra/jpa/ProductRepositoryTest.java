package com.mosinsa.product.infra.jpa;

import com.mosinsa.category.domain.CategoryRepository;
import com.mosinsa.category.domain.Category;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void equalsAndHashCode(){
		Product productA = productRepository.findById(ProductId.of("productId1")).get();
		Product productB = productRepository.findById(ProductId.of("productId1")).get();

		assertThat(productA).isEqualTo(productB).hasSameHashCodeAs(productB);

		Product productC = productRepository.findById(ProductId.of("productId2")).get();
		assertThat(productA).isNotEqualTo(productC).doesNotHaveSameHashCodeAs(productC);
	}

    @Test
    void create(){
		Category category = categoryRepository.save(Category.of("category1"));

		Product product = Product.of("name", 1000, category);
        Product saveProduct = productRepository.save(product);

        assertThat(product).isEqualTo(saveProduct);
        assertThat(product.getId()).isEqualTo(saveProduct.getId());
        assertThat(product.getName()).isEqualTo(saveProduct.getName());
    }

}