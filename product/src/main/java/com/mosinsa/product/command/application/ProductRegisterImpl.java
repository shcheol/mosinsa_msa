package com.mosinsa.product.command.application;

import com.mosinsa.category.application.CategoryService;
import com.mosinsa.category.domain.Category;
import com.mosinsa.product.command.domain.*;
import com.mosinsa.product.ui.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRegisterImpl implements ProductRegister {

	private final ProductRepository productRepository;
	private final CategoryService categoryService;
	private final StockPort stockPort;

	@Override
	public ProductId register(CreateProductRequest request) {
		Category category = categoryService.getCategory(request.categoryId());

		List<ProductOptions> productOptions = request.productOptions().stream()
				.map(po -> {
					List<ProductOptionsValue> productOptionsValues = po.productOptionValues()
							.stream()
							.map(pov -> ProductOptionsValue.of(pov.optionsValue(), Money.of(pov.changePrice()), pov.changeType()))
							.toList();
					ProductOptions of = ProductOptions.of(po.productOption());
					of.addOptionsValue(productOptionsValues);
					return of;
				}).toList();

		Product product = productRepository.save(Product.of(request.name(), request.price(), category));
		product.addOptions(productOptions);

		return product.getId();
	}
}
