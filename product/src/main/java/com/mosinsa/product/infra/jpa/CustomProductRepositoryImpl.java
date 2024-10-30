package com.mosinsa.product.infra.jpa;

import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

import static com.mosinsa.product.command.domain.QProduct.product;
import static com.mosinsa.reaction.command.domain.QReaction.reaction;


@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

	private final JPAQueryFactory factory;

	@Override
	public Page<Product> findByCondition(CategorySearchCondition condition, Pageable pageable) {

		List<Product> fetch = factory.select(product)
				.from(product)
				.leftJoin(product.brand)
				.fetchJoin()
				.where(
						category(condition.ids())
				)
				.orderBy(
						product.createdDate.desc()
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetch();
		return PageableExecutionUtils
				.getPage(fetch, pageable,
						factory.select(product.count())
								.from(product)
								.where(
										category(condition.ids())
								)::fetchOne);
	}

	@Override
	public Page<Product> findMyProducts(String memberId, Pageable pageable) {
		if (!StringUtils.hasText(memberId)) {
			throw new ProductException(ProductError.NOT_FOUNT_PRODUCT);
		}
		List<Product> fetch = factory.select(product)
				.from(product)
				.leftJoin(product.brand)
				.fetchJoin()
				.innerJoin(reaction)
				.on(product.id.id.eq(reaction.targetId))
				.where(
						checkMyReaction(memberId),
						checkTarget(TargetEntity.PRODUCT),
						checkReactionType(ReactionType.LIKES),
						checkActive()
				)
				.orderBy(reaction.lastModifiedDate.desc())
				.fetch();
		return PageableExecutionUtils
				.getPage(fetch, pageable,
						factory.select(product.count())
								.from(product)
								.where(
										checkMyReaction(memberId),
										checkTarget(TargetEntity.PRODUCT),
										checkReactionType(ReactionType.LIKES),
										checkActive()
								)::fetchOne);
	}

	private BooleanExpression checkActive() {
		return reaction.active.isTrue();
	}

	private BooleanExpression checkMyReaction(String memberId) {
		return reaction.memberId.eq(memberId);
	}

	private BooleanExpression checkReactionType(ReactionType likes) {
		return reaction.reactionType.eq(likes);
	}

	private BooleanExpression category(Set<String> ids) {
		return ids != null && !ids.isEmpty() ? product.category.id.id.in(ids) : null;
	}

	private BooleanExpression checkTarget(TargetEntity target) {
		return reaction.targetType.eq(target);
	}


}
