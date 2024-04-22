package com.mosinsa.order.infra.repository;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, OrderId>, CustomOrderRepository {

	@Query("select o from Order o inner join fetch o.orderProducts where o.id = :orderId")
	Optional<Order> findOrderDetailsById(@Param("orderId") OrderId orderId);
}
