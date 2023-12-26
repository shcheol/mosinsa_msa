package com.mosinsa.product.ui.request;

public record CreateProductRequest(String name, int price, String category, int stock) {
}
