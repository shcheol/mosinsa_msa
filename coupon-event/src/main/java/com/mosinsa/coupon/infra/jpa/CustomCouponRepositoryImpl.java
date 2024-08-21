package com.mosinsa.coupon.infra.jpa;

import com.mosinsa.coupon.command.domain.Coupon;
import com.mosinsa.coupon.command.domain.CouponState;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.mosinsa.coupon.command.domain.QCoupon.coupon;


@RequiredArgsConstructor
public class CustomCouponRepositoryImpl implements CustomCouponRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Coupon> findMyCoupons(String memberId) {

        return queryFactory.select(coupon)
                .from(coupon)
                .where(
                        coupon.memberId.eq(memberId),
                        availableCoupons())
                .orderBy(new OrderSpecifier<>(Order.DESC, coupon.issuedDate))
                .fetch();
    }

    private BooleanExpression availableCoupons() {
        return availableDate().and(availableState());
    }

    private BooleanExpression availableState() {
        return coupon.state.eq(CouponState.ISSUED);
    }

    private BooleanExpression availableDate() {
        return coupon.condition.duringDate.after(LocalDate.now());
    }
}
