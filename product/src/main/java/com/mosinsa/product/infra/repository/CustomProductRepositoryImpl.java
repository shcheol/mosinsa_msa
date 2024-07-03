package com.mosinsa.product.infra.repository;

import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.command.application.dto.QProductQueryDto;
import com.mosinsa.product.ui.request.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.mosinsa.product.command.domain.QProduct.product;


@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory factory;

    @Override
    public Page<ProductQueryDto> findByCondition(SearchCondition condition, Pageable pageable) {

        List<ProductQueryDto> fetch = factory.select(new QProductQueryDto(product))
                .from(product)
                .where(
                        category(condition.categoryId())
                )
                .orderBy(
                        product.createdDate.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
        return PageableExecutionUtils
                .getPage(fetch, pageable,
                        factory.select(product.count())
                                .from(product)
                                .where(
                                        category(condition.categoryId())
                                )::fetchOne);

    }

    private BooleanExpression category(String categoryId) {
        return categoryId != null ? product.category.id.id.eq(categoryId) : null;
    }


}
