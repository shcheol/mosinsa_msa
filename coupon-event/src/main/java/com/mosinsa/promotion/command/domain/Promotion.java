package com.mosinsa.promotion.command.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "promotion")
@Getter
public class Promotion {

    @EmbeddedId
    private PromotionId id;
    @Nonnull
    private String title;

    @Column(name = "contexts")
    private String context;

    @Embedded
    private PromotionPeriod period;

    public static Promotion create(String title, String context, PromotionPeriod period) {
        Promotion promotion = new Promotion();
        promotion.id = PromotionId.newId();
        promotion.title = title;
        promotion.context = context;
        promotion.period = period;
        return promotion;
    }

    protected Promotion() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promotion promotion)) return false;
        return Objects.equals(id, promotion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
