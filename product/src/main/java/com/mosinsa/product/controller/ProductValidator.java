package com.mosinsa.product.controller;

import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		log.info("is {} clazz supports", clazz);
		return ProductAddRequest.class.isAssignableFrom(clazz) || ProductUpdateRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductAddRequest request = (ProductAddRequest) target;

		String name = request.getName();
		Assert.hasText(name, "상품명은 필수입니다.");

		int price = request.getPrice();
		Assert.isTrue(price > 0, "상품 가격은 0보다 커야합니다.");

		int stock = request.getStock();
		Assert.isTrue(stock > 0, "상품 수량은 0보다 커야합니다.");

	}
}
