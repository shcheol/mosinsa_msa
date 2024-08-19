package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.command.domain.PromotionConditions;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.feignclient.order.OrderSummary;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NewMemberConditionStrategy implements ConditionStrategy {

    private final OrderAdapter orderAdapter;

    @Override
    public ConditionOption getConditionOption(String memberId) {

		List<OrderSummary> orderSummaries = orderAdapter.getMyOrders(memberId).orElse(List.of());
		if (orderSummaries.isEmpty()){
			return ConditionOption.NEW_MEMBER;
		}
		return ConditionOption.OLD_MEMBER;
    }

    @Override
    public boolean isSupport(PromotionConditions promotionConditions) {
        return PromotionConditions.NEW_MEMBER.equals(promotionConditions);
    }
}
