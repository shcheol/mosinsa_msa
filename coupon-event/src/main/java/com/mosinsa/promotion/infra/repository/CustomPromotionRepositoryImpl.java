package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import com.mosinsa.promotion.application.dto.QPromotionDto;
import com.querydsl.core.Tuple;
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

import static com.mosinsa.coupon.domain.QCoupon.coupon;
import static com.mosinsa.promotion.domain.QPromotion.promotion;

@RequiredArgsConstructor
public class CustomPromotionRepositoryImpl implements CustomPromotionRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable) {

		List<PromotionDto> fetch = queryFactory.select(
						new QPromotionDto(
								promotion.promotionId,
								promotion.title,
								promotion.context,
								promotion.quantity,
								promotion.discountPolicy,
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

	@Override
	public List<Tuple> stocksGroupByPromotion(PromotionSearchCondition condition) {
		return queryFactory.select(promotion.promotionId, promotion.count())
				.from(promotion)
				.leftJoin(coupon)
				.on(promotion.promotionId.eq(coupon.promotionId))
				.where(
						coupon.memberId.isNull(),
						proceeding(condition.now(), condition.proceeding())
				)
				.groupBy(promotion.promotionId)
				.fetch();
	}

	private BooleanExpression proceeding(LocalDateTime now, boolean proceeding) {
		return now != null && proceeding ? promotion.period.endDate.after(now).and(promotion.period.startDate.before(now)) : null;
	}
}
