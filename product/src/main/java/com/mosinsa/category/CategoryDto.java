package com.mosinsa.category;


import java.util.Comparator;
import java.util.List;

public record CategoryDto(String categoryId, String name, List<CategoryDto> childCategories) {
	public static CategoryDto convert(Category category){
		return new CategoryDto(category.getId().getId(),
				category.getName(),
				category.getChildren()
						.stream()
						.sorted(Comparator.comparing(Category::getName))
						.map(CategoryDto::convert)
						.toList()
				);
	}
}
