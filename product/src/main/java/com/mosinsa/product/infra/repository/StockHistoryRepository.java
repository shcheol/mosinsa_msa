package com.mosinsa.product.infra.repository;

import com.mosinsa.product.command.domain.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, String> {
}
