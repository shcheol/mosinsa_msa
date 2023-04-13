package com.shopping.mosinsa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.repository.CouponQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponQueueService {

	private final CouponQueueRepository repository;

	private final CouponService service;

	private final ObjectMapper om;

	private CouponEvent couponEvent;

	public CouponEvent getCouponEvent(){
		return couponEvent;
	}

	public void setCouponEvent(CouponEvent couponEvent){
		this.couponEvent = couponEvent;
	}

	public boolean enqueue(CouponIssuanceRequest request) {

		String key = String.format("%s%s", request.getEventName(), request.getCouponEventId());

		String value = getJsonFormatString(request);
		long time = new Timestamp(System.nanoTime()).getTime();

		return repository.enqueue(key, value, Long.valueOf(time).doubleValue());

	}

	public Long lookupOrder(CouponIssuanceRequest request) {
		String key = String.format("%s%s", request.getEventName(), request.getCouponEventId());
		String value = getJsonFormatString(request);
		Long rank = repository.rank(key, value);
		return rank;
	}

	public void dequeuePart(String key, int chunk){

		Set<String> range = repository.range(key, 0, chunk-1);

		for (String s : range) {
			CouponIssuanceRequest couponIssuanceRequest = readStringValue(s);
			System.out.println(couponIssuanceRequest);
			service.couponIssuanceRequest(readStringValue(s));
		}

		repository.dequeue(key, chunk);
	}

	private CouponIssuanceRequest readStringValue(String s){
		try {
			return om.readValue(s,CouponIssuanceRequest.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private String getJsonFormatString(CouponIssuanceRequest request) {
		try {
			return om.writeValueAsString(request);
		}catch (JsonProcessingException ex){
			throw new IllegalArgumentException(ex);
		}
	}
}
