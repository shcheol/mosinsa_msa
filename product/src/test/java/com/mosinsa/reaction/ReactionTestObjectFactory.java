package com.mosinsa.reaction;

import com.mosinsa.reaction.command.application.ReactionService;
import com.mosinsa.reaction.infra.kafka.ProduceTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ReactionTestObjectFactory {

    @Bean
    public ReactionService reactionService(){
        return new ReactionServiceStub(null, null);
    }

    @Bean
    public ProduceTemplate produceTemplate(){
        return new ProduceTemplateStub(null,null);
    }

}
