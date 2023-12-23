package com.mosinsa.product.infra.repository;

public interface CustomProductRepository {

    void likesProduct(String productId, String memberId);
}
