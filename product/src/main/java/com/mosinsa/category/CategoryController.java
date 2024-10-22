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
		List<CategoryDto> categoryList = categoryService.getCategoryList();
		return ResponseEntity.ok(categoryList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<CategoryDto>> getCategory(@PathVariable("id") @NotBlank String id, @ModelAttribute CategoryCondition condition){
		List<CategoryDto> categoryList = categoryService.getCategoryList();
		return ResponseEntity.ok(categoryList);
	}
}
