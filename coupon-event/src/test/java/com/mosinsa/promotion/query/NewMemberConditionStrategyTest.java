package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.infra.api.ResponseResult;
import com.mosinsa.promotion.infra.api.feignclient.order.OrderSummary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewMemberConditionStrategyTest {

    @Test
    void getConditionOptions() {
        NewMemberConditionStrategy newMemberConditionStrategy = new NewMemberConditionStrategy(customerId -> {
            if (customerId.isEmpty()) {
                return ResponseResult.empty();
            }
            return ResponseResult.execute(() ->
                    List.of(new OrderSummary("", "", "", "", 2L)));
        });

        Assertions.assertThat(newMemberConditionStrategy.getConditionOption("")).isEqualTo(ConditionOption.NEW_MEMBER);
        Assertions.assertThat(newMemberConditionStrategy.getConditionOption("xx")).isEqualTo(ConditionOption.OLD_MEMBER);
    }
}