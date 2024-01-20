package com.mosinsa.product.infra.repository;

import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.application.dto.QProductDto;
import com.mosinsa.product.ui.request.OrderEnum;
import com.mosinsa.product.ui.request.SearchCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.mosinsa.product.domain.product.QProduct.product;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory factory;

    @Override
    public Page<ProductDto> findByCondition(SearchCondition condition, Pageable pageable) {

        List<ProductDto> fetch = factory.select(new QProductDto(product))
                .from(product)
                .where(
                        category(condition.categoryId())
                )
                .orderBy(
                        orderSpecifier(condition)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
        return PageableExecutionUtils
                .getPage(fetch, pageable,
                        factory
                                .select(product.count())
                                .from(product)
                                .where(
                                        category(condition.categoryId())
                                )
                                .orderBy(
                                        orderSpecifier(condition)
                                )::fetchOne);

    }

    private OrderSpecifier[] orderSpecifier(SearchCondition condition) {
        return new OrderSpecifier[]{
                condition.name() == null ? null : condition.name().equals(OrderEnum.ASC) ? product.name.asc() : product.name.desc(),
                condition.price() == null ? null : condition.price().equals(OrderEnum.ASC) ? product.name.asc() : product.name.desc(),
                condition.likes() == null ? null : condition.likes().equals(OrderEnum.ASC) ? product.name.asc() : product.name.desc()};
    }

    private BooleanExpression category(String categoryId) {
        return categoryId != null ? product.category.id.id.eq(categoryId) : null;
    }


}
