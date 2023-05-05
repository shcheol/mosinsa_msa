package com.shopping.mosinsa.batch;

import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class CouponItemWriter implements ItemWriter<List<CouponIssuanceRequest>> {

    private final CouponService couponService;

    @Override
    public void write(Chunk<? extends List<CouponIssuanceRequest>> requests) throws Exception {

        List<? extends List<CouponIssuanceRequest>> items = requests.getItems();
        for (List<CouponIssuanceRequest> item : items) {
            for (CouponIssuanceRequest couponIssuanceRequest : item) {
                couponService.couponIssuanceRequest(couponIssuanceRequest);
            }
        }
    }
}
