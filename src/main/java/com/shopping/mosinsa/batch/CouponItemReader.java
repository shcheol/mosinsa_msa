package com.shopping.mosinsa.batch;

import com.shopping.mosinsa.dto.CouponEventWrapper;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.repository.CouponEventRepository;
import com.shopping.mosinsa.repository.CouponQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;
import java.util.Map;
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
    public Set<String> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        int chunk = 10;
        Optional<CouponEventWrapper> eventKey1 = eventRepository.findEventKey(eventKey);

        CouponEvent couponEvent = eventKey1.get().getCouponEvent();

        int quantity = couponEvent.getQuantity();


        Set<String> range = repository.range(eventKey, 0, chunk-1);

        System.out.println(range);
        size += range.size();

        if(size>quantity){
            return null;
        }
        return range;
    }
}
