package com.mosinsa.product.infra.jpa;

import com.mosinsa.category.domain.CategoryRepository;
import com.mosinsa.category.domain.Category;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.ProductRepository;
import com.mosinsa.product.command.domain.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
	void detail(){
		Product productA = productRepository.findProductDetailById(ProductId.of("productId1")).get();
		Product productB = productRepository.findProductDetailById(ProductId.of("productId1")).get();

		Stock stockA = productA.getStock();
		Stock stockB = productB.getStock();
		assertThat(stockA).isEqualTo(stockB).hasSameHashCodeAs(stockB);

		LocalDateTime createdDateA = productA.getCreatedDate();
		LocalDateTime createdDateB = productB.getCreatedDate();
		assertThat(createdDateA).isEqualTo(createdDateB).hasSameHashCodeAs(createdDateB);

		Category categoryA = productA.getCategory();
		Category categoryB = productB.getCategory();
		assertThat(categoryA).isEqualTo(categoryB).hasSameHashCodeAs(categoryB);

		Product productC = productRepository.findProductDetailById(ProductId.of("productId2")).get();
		Stock stockC = productC.getStock();
		assertThat(stockA).isNotEqualTo(stockC).doesNotHaveSameHashCodeAs(stockC);

		LocalDateTime createdDateC = productC.getCreatedDate();
		assertThat(createdDateA).isNotEqualTo(createdDateC).doesNotHaveSameHashCodeAs(createdDateC);

		Category categoryC = productC.getCategory();
		assertThat(categoryA).isNotEqualTo(categoryC).doesNotHaveSameHashCodeAs(categoryC);


	}

    @Test
	@Commit
	@Transactional
    void create(){
		Category category = categoryRepository.save(Category.of("category1"));

		Product product = Product.create("name", 1000, category, 10);
        Product saveProduct = productRepository.save(product);

        assertThat(product).isEqualTo(saveProduct);
        assertThat(product.getId()).isEqualTo(saveProduct.getId());
        assertThat(product.getName()).isEqualTo(saveProduct.getName());
    }

}