package com.mosinsa.category;


public record CategoryDto(String categoryId, String name) {
	public static CategoryDto convert(Category category){
		return new CategoryDto(category.getId().getId(), category.getName());
	}
}
