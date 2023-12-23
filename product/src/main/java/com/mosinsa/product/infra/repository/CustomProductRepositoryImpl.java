package com.mosinsa.product.infra.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.mosinsa.product.domain.product.QProduct.product;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository{

    private final JPAQueryFactory factory;


    @Override
    public void likesProduct(String productId, String memberId) {
        factory.select(product)
                .from(product);
    }
}
