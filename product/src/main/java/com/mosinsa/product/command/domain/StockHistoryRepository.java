package com.mosinsa.product.command.domain;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockHistoryRepository extends Repository<StockHistory, Long> {

	Optional<StockHistory> findById(Long id);
	StockHistory save(StockHistory stockHistory);
	List<StockHistory> saveAll(Iterable<StockHistory> entities);
	List<StockHistory> findStockHistoriesByOrderNum(@Param("orderNum") String orderNum);
}
