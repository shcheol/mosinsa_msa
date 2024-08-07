package com.mosinsa.reaction.ui;

import com.mosinsa.reaction.query.application.ReactionReader;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;

public class ReactionReaderStub implements ReactionReader {
    @Override
    public boolean hasReacted(ReactionSearchCondition condition) {
        return true;
    }

    @Override
    public long total(ReactionSearchCondition condition) {
        return 10;
    }
}
