package com.mosinsa.product.ui.request;

public record SearchCondition(OrderEnum name, OrderEnum price, OrderEnum likes, String categoryId) {
}
