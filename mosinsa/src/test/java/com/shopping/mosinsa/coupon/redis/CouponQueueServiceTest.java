package com.shopping.mosinsa.coupon.redis;

import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.entity.Customer;
import com.shopping.mosinsa.entity.CustomerGrade;
import com.shopping.mosinsa.repository.CouponQueueRepository;
import com.shopping.mosinsa.repository.CustomerRepository;
import com.shopping.mosinsa.service.CouponQueueService;
import com.shopping.mosinsa.service.CouponService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.shopping.mosinsa.coupon.CouponSteps.쿠폰발급요청_생성;
import static com.shopping.mosinsa.coupon.CouponSteps.쿠폰이벤트생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponQueueServiceTest {

	@Autowired
	CouponQueueRepository repository;

	@Autowired
	CouponQueueService service;

	@Autowired
	CouponService couponService;

	@Autowired
	CustomerRepository customerRepository;

	String eventName = "test";
	Long couponEventId=1L;

	@AfterEach
	void cleanup(){
		repository.dequeue(String.format("%s%s",eventName, couponEventId),Long.MAX_VALUE);
	}

	@Test
	void 선착순대기열입장_성공(){
		CouponIssuanceRequest request = 쿠폰발급요청_생성(eventName, couponEventId, 1L);
		boolean enqueue = service.enqueue(request);
		assertThat(enqueue).isTrue();

	}

	@Test
	void 선착순대기열입장_중복(){
		CouponIssuanceRequest request = 쿠폰발급요청_생성(eventName, couponEventId,1L);
		boolean enqueue = service.enqueue(request);
		assertThat(enqueue).isTrue();

		enqueue = service.enqueue(request);
		assertThat(enqueue).isFalse();
	}

	@Test
	void 내순서확인(){
		int limit=100;
		CouponIssuanceRequest request;
		for (long i=0;i<limit;i++){
			request = 쿠폰발급요청_생성(eventName, couponEventId, i);
			service.enqueue(request);
			assertThat(service.lookupOrder(request)).isEqualTo(i);
		}
	}

	@Test
	void 대기열처리(){
		int limit=100;
		CouponEvent couponEvent = couponService.createCouponEvent(쿠폰이벤트생성_요청(limit));

		List<CouponIssuanceRequest> requests = new ArrayList<>();
		for (long i=0;i<limit;i++) {
			Customer customer = new Customer(CustomerGrade.BRONZE);
			customerRepository.save(customer);
			requests.add(쿠폰발급요청_생성(couponEvent.getEventName(), couponEvent.getId(), customer.getId()));
		}

		for (int i=0;i<limit;i++){
			service.enqueue(requests.get(i));
			assertThat(service.lookupOrder(requests.get(i))).isEqualTo(i);
		}

//		String key = String.format("%s%s",couponEvent.getEventName(), couponEvent.getId());
//		service.dequeuePart(key, limit-1);
//		Set<String> range = repository.range(key, 0, Integer.MAX_VALUE);
//		Assertions.assertThat(range.size()).isEqualTo(1);
	}


	@Test
	void 대기열잘라서처리(){
		int limit=50;
		CouponEvent couponEvent = couponService.createCouponEvent(쿠폰이벤트생성_요청(limit));

		CouponIssuanceRequest request;

		for (long i=0;i<limit;i++){
			Customer customer = new Customer(CustomerGrade.BRONZE);
			customerRepository.save(customer);
			request = 쿠폰발급요청_생성(couponEvent.getEventName(), couponEvent.getId(), customer.getId());
			service.enqueue(request);
			assertThat(service.lookupOrder(request)).isEqualTo(i);
		}

		//scheduler
		int chunk = 10;
		String key = String.format("%s%s",couponEvent.getEventName(), couponEvent.getId());
		System.out.println("key: "+key);
		while(limit>0) {
			service.dequeuePart(key, chunk);
			Set<String> range = repository.range(key, 0, Integer.MAX_VALUE);
			Assertions.assertThat(range.size()).isEqualTo(limit - chunk);
			limit-=chunk;
		}
	}


}