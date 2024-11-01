package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.StockStatus;

public record StockDto(Long current, Long total, StockStatus status) {
}
