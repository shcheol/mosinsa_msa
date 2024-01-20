package com.mosinsa.product.ui;

import com.mosinsa.product.application.CategoryService;
import com.mosinsa.product.application.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/category"))
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> categoryList = categoryService.getCategoryList();
		return ResponseEntity.ok(categoryList);
	}
}
