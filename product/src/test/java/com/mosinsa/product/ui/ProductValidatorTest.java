package com.mosinsa.product.ui;

import com.mosinsa.product.ui.request.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {

	@Test
	void supports() {
		ProductValidator productValidator = new ProductValidator();
		assertThat(productValidator.supports(CreateProductRequest.class)).isTrue();
	}

	@Test
	void supportsFalse() {
		ProductValidator productValidator = new ProductValidator();
		assertThat(productValidator.supports(TestClass.class)).isFalse();
	}

	@Test
	void validate() {
		ProductValidator productValidator = new ProductValidator();
		CreateProductRequest request = new CreateProductRequest("name", 1000, "category", 10);
		productValidator.validate(request, new BindException(new MapBindingResult(new HashMap<>(), "name") {
		}));
	}
	@Test
	void validateEx1() {
		ProductValidator productValidator = new ProductValidator();
		CreateProductRequest request = new CreateProductRequest("", 1000, "category", 10);
		BindException errors = new BindException(new MapBindingResult(new HashMap<>(), "name") {
		});

		assertThrows(RuntimeException.class, () -> productValidator.validate(request, errors));
	}
	@Test
	void validateEx2() {
		ProductValidator productValidator = new ProductValidator();
		CreateProductRequest request = new CreateProductRequest("name", 0, "category", 10);
		BindException errors = new BindException(new MapBindingResult(new HashMap<>(), "name") {
		});

		assertThrows(RuntimeException.class, () -> productValidator.validate(request, errors));
	}

	@Test
	void validateEx3() {
		ProductValidator productValidator = new ProductValidator();
		CreateProductRequest request = new CreateProductRequest("name", 10, "", 10);
		BindException errors = new BindException(new MapBindingResult(new HashMap<>(), "name") {
		});

		assertThrows(RuntimeException.class, () -> productValidator.validate(request, errors));
	}

	@Test
	void validateEx4() {
		ProductValidator productValidator = new ProductValidator();
		CreateProductRequest request = new CreateProductRequest("name", 10, "category", 0);
		BindException errors = new BindException(new MapBindingResult(new HashMap<>(), "name") {
		});

		assertThrows(RuntimeException.class, () -> productValidator.validate(request, errors));
	}



	static class TestClass {

	}

}