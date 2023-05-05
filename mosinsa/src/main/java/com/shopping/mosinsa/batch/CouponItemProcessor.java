package com.shopping.mosinsa.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CouponItemProcessor implements ItemProcessor<Set<String>, List<CouponIssuanceRequest>> {

    @Autowired
    ObjectMapper om;

    @Override
    public List<CouponIssuanceRequest> process(Set<String> items) throws Exception {

        return new ArrayList<>(items.stream().map(this::readStringValue).toList());
    }
    private CouponIssuanceRequest readStringValue(String s){
        try {
            return om.readValue(s,CouponIssuanceRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
