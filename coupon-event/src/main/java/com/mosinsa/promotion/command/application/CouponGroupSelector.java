package com.mosinsa.promotion.command.application;

import com.mosinsa.promotion.command.domain.CouponGroupInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Component
public class CouponGroupSelector implements Function<List<CouponGroupInfo>, CouponGroupInfo> {
    @Override
    public CouponGroupInfo apply(List<CouponGroupInfo> couponGroupInfos) {
        List<CouponGroupInfo> availableCouponGroups = couponGroupInfos.stream()
                .filter(couponGroupInfo -> couponGroupInfo.getQuantity() > 0)
                .toList();
        return availableCouponGroups.get(new Random().nextInt(availableCouponGroups.size()));
    }
}
