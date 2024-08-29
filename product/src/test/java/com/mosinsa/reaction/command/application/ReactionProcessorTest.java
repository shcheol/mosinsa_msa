package com.mosinsa.reaction.command.application;

import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.command.domain.ReactionId;
import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.command.domain.ReactionRepository;
import com.mosinsa.reaction.query.application.ReactionReaderImpl;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReactionProcessorTest {

    @Autowired
    ReactionProcessor processor;

    @Autowired
    ReactionReaderImpl reader;

    @Autowired
    ReactionRepository repository;

    @Test
    void reaction() {
        ReactionSearchCondition condition = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1xx", ReactionType.LIKES, "memberId1");

        String id = processor.reaction(condition);

        Reaction reaction = repository.findById(ReactionId.of(id)).get();
        assertThat(reaction.getReactionType()).isEqualTo(ReactionType.LIKES);
        assertThat(reaction.getTargetType()).isEqualTo(TargetEntity.PRODUCT);
        assertThat(reaction.getTargetId()).isEqualTo("productId1xx");
		assertThat(reaction.getMemberId()).isEqualTo("memberId1");
        assertThat(reaction.isActive()).isTrue();

		assertThrows(AlreadySameStateException.class, ()->processor.reaction(condition));
    }

    @Test
    void reactionCancel() {
        ReactionSearchCondition condition = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId2");

        String id = processor.cancel(condition);

        Reaction reaction = repository.findById(ReactionId.of(id)).get();
        assertThat(reaction.getReactionType()).isEqualTo(ReactionType.LIKES);
        assertThat(reaction.getTargetType()).isEqualTo(TargetEntity.PRODUCT);
        assertThat(reaction.getTargetId()).isEqualTo("productId1");
        assertThat(reaction.isActive()).isFalse();

		assertThrows(AlreadySameStateException.class, ()->processor.cancel(condition));

    }
}