package com.mosinsa.product.infra.repository;

import com.mosinsa.product.command.domain.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, String> {

	List<StockHistory> findStockHistoriesByOrderNum(@Param("orderNum") String orderNum);
}
