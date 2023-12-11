package com.mosinsa.product.ui.request;

public record CreateProductRequest(String name, int price, String categoryId, int stock) {
}
