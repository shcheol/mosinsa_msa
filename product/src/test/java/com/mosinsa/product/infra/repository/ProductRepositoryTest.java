package com.mosinsa.product.infra.repository;

import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

    @Test
    void create(){
		Category category = categoryRepository.save(Category.of("category1"));

		Product product = Product.create("name", 1000, category, 10);
        Product saveProduct = productRepository.save(product);

        assertThat(product).isEqualTo(saveProduct);
        assertThat(product.getId()).isEqualTo(saveProduct.getId());
        assertThat(product.getName()).isEqualTo(saveProduct.getName());
        assertThat(product.getCategory()).isEqualTo(saveProduct.getCategory());
        assertThat(product.getStock()).isEqualTo(saveProduct.getStock());
        assertThat(product.getLikes().getTotal()).isZero();
        assertThat(product.getLikes().getLikesMember()).isEmpty();
    }

	@Test
	void likes(){
		Category category = categoryRepository.save(Category.of("category1"));
		Product product = productRepository.save(Product.create("name", 1000, category, 10));
		productRepository.save(Product.create("name", 1000, category, 10));

		assertThat(product.getLikes().getTotal()).isZero();

		product.likes("memberId1");
		assertThat(product.getLikes().getTotal()).isEqualTo(1);

		product.likes("memberId2");
		assertThat(product.getLikes().getTotal()).isEqualTo(2);


		product.likes("memberId1");
		assertThat(product.getLikes().getTotal()).isEqualTo(1);

	}

}