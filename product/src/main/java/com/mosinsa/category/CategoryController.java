package com.mosinsa.category;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/category"))
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> categoryList = categoryService.getAllCategoriesFromRoot();
		return ResponseEntity.ok(categoryList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") @NotBlank String id){
		CategoryDto categorySet = categoryService.getCategorySetFromParent(id);
		return ResponseEntity.ok(categorySet);
	}
}
