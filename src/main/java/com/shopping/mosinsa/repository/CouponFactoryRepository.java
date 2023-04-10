package com.shopping.mosinsa.repository;

import com.shopping.mosinsa.entity.Coupon;
import com.shopping.mosinsa.entity.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CouponFactoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public int bulkInsert(Coupon coupon, int stock){

        String sql = "INSERT INTO coupon (coupon_event_id, discount_policy, expiration_period, status, created_date, last_modified_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";


        int[] update = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                LocalDateTime now = LocalDateTime.now();
                ps.setObject(1, coupon.getCouponEvent().getId());
                ps.setString(2, coupon.getDiscountPolicy().name());
                ps.setObject(3, coupon.getExpirationPeriod());
                ps.setString(4, CouponStatus.UNUSED.name());
                ps.setObject(5, now);
                ps.setObject(6, now);
            }

            @Override
            public int getBatchSize() {
                return stock;
            }
        });

        return update.length;
    }

}
