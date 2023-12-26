package com.mosinsa.product.ui;

import com.mosinsa.product.ui.request.CreateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		log.info("is {} clazz supports", clazz);
		return CreateProductRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateProductRequest request = (CreateProductRequest) target;

		String name = request.name();
		Assert.hasText(name, "상품명은 필수입니다.");

		int price = request.price();
		Assert.isTrue(price > 0, "상품 가격은 0보다 커야합니다.");

		String categoryId = request.category();
		Assert.hasText(categoryId, "카테고리는 필수입니다.");

		int stock = request.stock();
		Assert.isTrue(stock > 0, "상품 수량은 0보다 커야합니다.");

	}
}
