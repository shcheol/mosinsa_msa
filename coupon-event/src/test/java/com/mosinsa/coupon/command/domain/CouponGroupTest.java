package com.mosinsa.coupon.command.domain;

import com.mosinsa.DataConfig;
import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CouponGroupTest extends InMemoryJpaTest {

	@Autowired
	CouponGroupRepository repository;

	@Test
	void save() {
		CouponGroup of = CouponGroup.of("name", 2, LocalDate.now(), DiscountPolicy.NONE);
		CouponGroup save = repository.save(of);
		CouponGroup protectedConstructor = new CouponGroup();
		CouponGroup other = CouponGroup.of("", 3, LocalDate.now(), DiscountPolicy.NONE);

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(of, save, protectedConstructor, other);
		assertThat(b).isTrue();
	}

	@Test
	void equalsAndHashcode() {
		CouponGroup of = CouponGroup.of("", 2, LocalDate.now(), DiscountPolicy.NONE);
		CouponGroup save = repository.save(of);
		CouponGroup protectedConstructor = new CouponGroup();
		CouponGroup other = CouponGroup.of("", 3, LocalDate.now(), DiscountPolicy.NONE);

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(of, save, protectedConstructor, other);
		assertThat(b).isTrue();
	}

}