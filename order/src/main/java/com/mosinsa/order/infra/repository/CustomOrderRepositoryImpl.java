package com.mosinsa.order.infra.repository;

import com.mosinsa.order.ui.request.SearchCondition;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.mosinsa.order.command.domain.QOrder.order;


@AllArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

	private final JPAQueryFactory factory;

	@Override
	public Page<Order> findOrdersByCondition(SearchCondition condition, Pageable pageable) {

		List<Order> fetch = factory.selectFrom(order)
				.where(
						customer(condition.getCustomerId()),
						dateBetween(condition.getStartDate(), condition.getEndDate()),
						status(condition.getStatus())
				)
				.orderBy(order.createdDate.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetch();

		return PageableExecutionUtils
				.getPage(fetch, pageable,
						factory
								.select(order.count())
								.from(order)
								.where(
										customer(condition.getCustomerId()),
										dateBetween(condition.getStartDate(), condition.getEndDate()),
										status(condition.getStatus())
								).orderBy(order.createdDate.desc())::fetchOne);
	}

	private BooleanExpression dateBetween(LocalDateTime startDate, LocalDateTime endDate) {
		if (startDate == null && endDate == null) {
			return null;
		}
		return order.createdDate.between(startDate, endDate);
	}

	private BooleanExpression customer(String customerId) {
		return customerId == null ? null : order.customerId.eq(customerId);
	}

	private BooleanExpression status(OrderStatus status) {
		return OrderStatus.contains(status) ? order.status.eq(status) : null;
	}

}
