package com.mosinsa.reaction.ui;

import com.mosinsa.reaction.qeury.application.ReactionReader;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;

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
