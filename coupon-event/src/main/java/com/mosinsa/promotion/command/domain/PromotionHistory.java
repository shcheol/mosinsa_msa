package com.mosinsa.promotion.command.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
public class PromotionHistory extends BaseEntity {

    @Nonnull
    private String memberId;

    @CreatedDate
    private LocalDateTime participateDate;

    public static PromotionHistory of(String memberId) {
        PromotionHistory promotionHistory = new PromotionHistory();
        promotionHistory.memberId = memberId;
        return promotionHistory;
    }

    protected PromotionHistory() {
    }

}
