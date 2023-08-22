package com.mosinsa.couponissue.batch;

import com.mosinsa.couponissue.dto.CouponEventWrapper;
import com.mosinsa.couponissue.entity.CouponEvent;
import com.mosinsa.couponissue.repository.CouponEventRepository;
import com.mosinsa.couponissue.repository.CouponQueueRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class CouponItemReader implements ItemReader<Set<String>> {

    private final CouponQueueRepository repository;
    private final CouponEventRepository eventRepository;
    private final String eventKey;
    int size=0;

    @Override
    public Set<String> read() throws Exception {

        int chunk = 10;
        Optional<CouponEventWrapper> eventKey1 = eventRepository.findEventKey(eventKey);
        System.out.println(eventKey1.get());

        CouponEvent couponEvent = eventKey1.get().getCouponEvent();

        int quantity = couponEvent.getQuantity();

        System.out.println("[eventKey]"+eventKey + "[quantity]" + quantity);

        Set<String> range = repository.range(eventKey, 0, chunk-1);

        if (range.size()>0){
            repository.dequeue(eventKey,chunk);
            size += range.size();
        }

        Thread.sleep(5000);

        if(quantity<=0){
            return null;
        }
        return range;
    }
}
