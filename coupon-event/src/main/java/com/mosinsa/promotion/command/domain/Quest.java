package com.mosinsa.promotion.command.domain;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Quest extends BaseEntity {

    public static Quest create() {
        Quest quest = new Quest();

        return quest;
    }

    protected Quest() {
    }
}
