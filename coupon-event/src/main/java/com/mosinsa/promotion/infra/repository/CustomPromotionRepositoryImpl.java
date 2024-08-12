package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.query.dto.PromotionSummary;
import com.mosinsa.promotion.query.dto.PromotionSearchCondition;
import com.mosinsa.promotion.query.dto.QPromotionSummary;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.mosinsa.promotion.command.domain.QPromotion.promotion;

@RequiredArgsConstructor
public class CustomPromotionRepositoryImpl implements CustomPromotionRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<PromotionSummary> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable) {

		List<PromotionSummary> fetch = queryFactory.select(
						new QPromotionSummary(
								promotion.id,
								promotion.title,
								promotion.context,
								promotion.period
						)
				).from(promotion)
				.where(
						proceeding(condition.now(), condition.proceeding())
				).orderBy(
						new OrderSpecifier<>(Order.DESC, promotion.period.endDate),
						new OrderSpecifier<>(Order.ASC, promotion.period.startDate),
						new OrderSpecifier<>(Order.ASC, promotion.title)
				).offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Long> count = queryFactory.select(promotion.count())
				.from(promotion)
				.where(
						proceeding(condition.now(), condition.proceeding())
				);
		return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
	}

	private BooleanExpression proceeding(LocalDateTime now, boolean proceeding) {
		return now != null && proceeding ? promotion.period.endDate.after(now).and(promotion.period.startDate.before(now)) : null;
	}
}
