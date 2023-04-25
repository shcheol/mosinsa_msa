package com.shopping.mosinsa;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@EnableBatchProcessing
@SpringBootApplication
public class MosinsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MosinsaApplication.class, args);
    }

}
