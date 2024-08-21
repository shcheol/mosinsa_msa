package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.mosinsa.promotion.command.domain.ConditionOption.NEW_MEMBER;
import static org.junit.jupiter.api.Assertions.*;

class PromotionConditionOptionTest extends InMemoryJpaTest {

    @Autowired
    PromotionRepository repository;

    @Test
    void equalsAndHashcode(){
        PromotionConditionOption option1 = repository.findById(PromotionId.of("promotion1")).get()
                .getPromotionCondition().getPromotionConditionOptions().stream().filter(options -> options.getId().equals(1L)).findAny().get();
        PromotionConditionOption option2 = repository.findById(PromotionId.of("promotion1")).get()
                .getPromotionCondition().getPromotionConditionOptions().stream().filter(options -> options.getId().equals(1L)).findAny().get();
        PromotionConditionOption protectedConstructor = new PromotionConditionOption();
        PromotionConditionOption other = repository.findById(PromotionId.of("promotion1")).get()
                .getPromotionCondition().getPromotionConditionOptions().stream().filter(options -> options.getId().equals(2L)).findAny().get();

        boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(option1, option2, protectedConstructor, other);
        Assertions.assertThat(b).isTrue();
    }

    @Test
    void of(){
        PromotionCondition condition = PromotionCondition.of(PromotionConditions.NEW_OR_OLD_MEMBER);
        Assertions.assertThat(condition.getPromotionConditionOptions()).isEmpty();

        PromotionConditionOption of = PromotionConditionOption.of(NEW_MEMBER, condition);
        Assertions.assertThat(of.getOptionName()).isEqualTo(NEW_MEMBER);
        Assertions.assertThat(of.getPromotionCondition()).isEqualTo(condition);
        Assertions.assertThat(condition.getPromotionConditionOptions()).hasSize(1);
    }

}