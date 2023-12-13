package com.mosinsa.product.application.dto;


import com.mosinsa.product.domain.category.Category;

public record CategoryDto(String categoryId, String name) {
	public static CategoryDto convert(Category category){
		return new CategoryDto(category.getId().getId(), category.getName());
	}
}
