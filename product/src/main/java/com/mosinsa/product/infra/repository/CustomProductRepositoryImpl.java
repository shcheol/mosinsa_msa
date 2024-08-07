package com.mosinsa.product.infra.repository;

import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.command.application.dto.QProductQueryDto;
import com.mosinsa.product.ui.request.SearchCondition;
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

import static com.mosinsa.product.command.domain.QProduct.product;
import static com.mosinsa.reaction.command.domain.QReaction.reaction;


@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

	private final JPAQueryFactory factory;

	@Override
	public Page<ProductQueryDto> findByCondition(SearchCondition condition, Pageable pageable) {

		List<ProductQueryDto> fetch = factory.select(new QProductQueryDto(product))
				.from(product)
				.where(
						category(condition.categoryId())
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
										category(condition.categoryId())
								)::fetchOne);

	}

	@Override
	public Page<ProductQueryDto> findMyProducts(String memberId, Pageable pageable) {
		if (!StringUtils.hasText(memberId)){
			throw new ProductException(ProductError.NOT_FOUNT_PRODUCT);
		}
		List<ProductQueryDto> fetch = factory.select(new QProductQueryDto(product))
				.from(product)
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

	private BooleanExpression category(String categoryId) {
		return categoryId != null ? product.category.id.id.eq(categoryId) : null;
	}

	private BooleanExpression checkTarget(TargetEntity target) {
		return reaction.targetType.eq(target);
	}


}
