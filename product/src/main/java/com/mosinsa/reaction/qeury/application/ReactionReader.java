package com.mosinsa.reaction.qeury.application;

import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;

public interface ReactionReader {
    boolean hasReacted(ReactionSearchCondition condition);

    long total(ReactionSearchCondition condition);
}
