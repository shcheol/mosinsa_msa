package com.mosinsa.review.ui;

import com.mosinsa.review.command.application.ReviewService;
import com.mosinsa.review.query.application.ReviewQueryService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ReviewPresentationObjectFactory {

	@Bean
	public ReviewQueryService reviewQueryService(){
		return new ReviewQueryServiceStub();
	}


	@Bean
	public ReviewService reviewService(){
		return new ReviewServiceStub();
	}
}
