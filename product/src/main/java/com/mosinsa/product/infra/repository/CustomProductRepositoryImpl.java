package com.mosinsa.product.infra.repository;

import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.application.dto.QProductDto;
import com.mosinsa.product.ui.request.SearchCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.mosinsa.product.domain.product.QProduct.product;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

	private final JPAQueryFactory factory;

	@Override
	public List<ProductDto> findByCondition(SearchCondition condition) {

		return factory.select(new QProductDto(product))
				.from(product)
				.where(

				).fetch();
	}

}
