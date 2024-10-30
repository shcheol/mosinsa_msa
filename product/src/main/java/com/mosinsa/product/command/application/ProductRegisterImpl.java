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

		List<ProductOptionsValue> of = List.of(
				ProductOptionsValue.of("FREE", Money.of(0), ChangeType.HOLD)
		);
		for (ProductOptionsValue productOptionsValue : of) {
			productOptionsValue.addStock(Stock.of(200));
		}
		ProductOptions po = ProductOptions.of(ProductOption.SIZE);
		po.addOptionsValue(of);

		Product product = productRepository.save(Product.of(request.name(), request.price(), category));
		product.addOptions(List.of(po));

		return product.getId();
	}
}
