package com.mosinsa.coupon.infra.repository;

import com.mosinsa.coupon.command.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcCouponRepositoryImpl implements JdbcCouponRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int batchInsert(List<Coupon> couponList) {

        String sql = "INSERT INTO coupon (coupon_id, promotion_id, issued_date, discount_policy, during_date, member_id, state) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        int[] ints = jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setObject(1, couponList.get(i).getId().getId());
                        ps.setObject(2, couponList.get(i).getPromotionId().getId());
                        ps.setObject(3, null);
						ps.setObject(4, couponList.get(i).getDetails().getDiscountPolicy().name());
                        ps.setObject(5, couponList.get(i).getDetails().getDuringDate());
                        ps.setObject(6, null);
                        ps.setObject(7, couponList.get(i).getState().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return couponList.size();
                    }
                });
        return ints.length;
    }
}
