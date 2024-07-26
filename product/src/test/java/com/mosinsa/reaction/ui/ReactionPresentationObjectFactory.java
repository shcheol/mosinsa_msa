package com.mosinsa.reaction.ui;

import com.mosinsa.reaction.command.application.ReactionService;
import com.mosinsa.reaction.qeury.application.ReactionReader;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ReactionPresentationObjectFactory {

    @Bean
    public ReactionService reactionService(){
        return new ReactionServiceStub();
    }

    @Bean
    public ReactionReader reactionReader(){
        return new ReactionReaderStub();
    }
}
