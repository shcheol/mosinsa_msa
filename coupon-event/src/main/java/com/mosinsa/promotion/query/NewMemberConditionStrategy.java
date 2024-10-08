package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.command.domain.PromotionConditions;
import com.mosinsa.promotion.infra.api.ExternalServerException;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.feignclient.order.OrderSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewMemberConditionStrategy implements ConditionStrategy {

	private final OrderAdapter orderAdapter;

	@Override
	public ConditionOption getConditionOption(String memberId) {

		if (orderAdapter.getMyOrders(memberId).orElseThrow().isEmpty()) {
			return ConditionOption.NEW_MEMBER;
		}
		return ConditionOption.OLD_MEMBER;
	}

	@Override
	public boolean isSupport(PromotionConditions promotionConditions) {
		return PromotionConditions.NEW_OR_OLD_MEMBER.equals(promotionConditions);
	}
}
