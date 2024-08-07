package com.mosinsa.reaction.query.application;

import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;

public interface ReactionReader {
    boolean hasReacted(ReactionSearchCondition condition);

    long total(ReactionSearchCondition condition);
}
