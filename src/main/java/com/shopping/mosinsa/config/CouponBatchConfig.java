package com.shopping.mosinsa.config;

import com.shopping.mosinsa.batch.CouponItemProcessor;
import com.shopping.mosinsa.batch.CouponItemReader;
import com.shopping.mosinsa.batch.CouponItemWriter;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.repository.CouponEventRepository;
import com.shopping.mosinsa.repository.CouponQueueRepository;
import com.shopping.mosinsa.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Set;

@Slf4j
@Configuration

public class CouponBatchConfig {


    private final JobRepository jobRepository;
    private final JobBuilder jobBuilder;
    private final StepBuilder stepBuilder;
    private final CouponQueueRepository couponQueueRepository;

    private final CouponEventRepository eventRepository;
    private final CouponService couponService;
    private final PlatformTransactionManager platformTransactionManager;

    public CouponBatchConfig(JobRepository jobRepository, CouponQueueRepository couponQueueRepository,CouponEventRepository eventRepository,CouponService couponService, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.jobBuilder = new JobBuilder("couponJobBuilder", jobRepository);
        this.stepBuilder = new StepBuilder("couponStepBuilder", jobRepository);
        this.couponQueueRepository = couponQueueRepository;
        this.eventRepository = eventRepository;
        this.couponService = couponService;
        this.platformTransactionManager = platformTransactionManager;
    }

    private static final int CHUNK_SIZE = 1;

    @Bean
    public Job couponJob() {
        log.info("couponJob");
        return jobBuilder
                .start(couponStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step couponStep(@Value("#{jobParameters[key]}") String eventKey) {
        log.info("couponStep");

        return stepBuilder
                .<Set<String>, List<CouponIssuanceRequest>>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(couponItemReader(eventKey))
                .processor(couponItemProcessor())
                .writer(couponItemWriter())
                .faultTolerant()
                .skip(IllegalArgumentException.class)
                .skipLimit(Integer.MAX_VALUE)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Set<String>> couponItemReader(@Value("#{jobParameters[key]}") String eventKey){
        return new CouponItemReader(couponQueueRepository, eventRepository, eventKey);
    }

    @Bean
    @StepScope
    public ItemProcessor<Set<String>, List<CouponIssuanceRequest>> couponItemProcessor(){
        return new CouponItemProcessor();
    }

    @Bean
    @StepScope
    public ItemWriter<List<CouponIssuanceRequest>> couponItemWriter(){

        return new CouponItemWriter(couponService);
    }

}
