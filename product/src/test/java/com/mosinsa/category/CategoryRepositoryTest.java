package com.mosinsa.category;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository repository;

	@Test
	void equalsAndHashcode(){

		Category test = Category.of("test");
		Category save = repository.save(test);
		assertThat(test).isEqualTo(test).isEqualTo(save).hasSameHashCodeAs(save)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		Category testxxxx = Category.of("testxxxx");
		Category protectedConstructor = new Category();

		assertThat(test).isNotEqualTo(testxxxx);
		assertThat(protectedConstructor).isNotEqualTo(test);

	}
}