package com.mosinsa.promotion.query.conditionstrategy;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.infra.api.CustomPageImpl;
import com.mosinsa.promotion.infra.api.ResponseResult;
import com.mosinsa.promotion.infra.api.httpinterface.OrderSummary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

class NewMemberConditionStrategyTest {

    @Test
    void getConditionOptions() {
        NewMemberConditionStrategy newMemberConditionStrategy = new NewMemberConditionStrategy(customerId -> {
            if (customerId.isEmpty()) {
                return ResponseResult.execute(() -> new CustomPageImpl<>(List.of(),0,1,0L, null));
            }
            return ResponseResult.execute(() ->
                    new CustomPageImpl<>(List.of(new OrderSummary("", "", "", "", 2L)),0,1,0L, null));
        });

        Assertions.assertThat(newMemberConditionStrategy.getConditionOption("")).isEqualTo(ConditionOption.NEW_MEMBER);
        Assertions.assertThat(newMemberConditionStrategy.getConditionOption("xx")).isEqualTo(ConditionOption.OLD_MEMBER);
    }
}