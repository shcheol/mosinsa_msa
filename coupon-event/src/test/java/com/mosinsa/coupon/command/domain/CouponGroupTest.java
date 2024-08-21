package com.mosinsa.coupon.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CouponGroupTest extends InMemoryJpaTest {

	@Autowired
	CouponGroupRepository repository;

	@Test
	void save() {
		CouponGroup of = CouponGroup.of("name", 2, LocalDate.now(), DiscountPolicy.NONE);
		CouponGroup save = repository.save(of);

		assertThat(save.getId()).isNotNull();
		assertThat(save.getName()).isEqualTo("name");
		assertThat(save.getCreatedDate()).isNotNull();
		assertThat(save.getLastModifiedDate()).isNotNull();
	}

	@Test
	void equalsAndHashcode() {
		CouponGroup of = CouponGroup.of("a", 2, LocalDate.now(), DiscountPolicy.NONE);
		CouponGroup save = repository.save(of);
		CouponGroup protectedConstructor = new CouponGroup();
		CouponGroup other = CouponGroup.of("ab", 3, LocalDate.now(), DiscountPolicy.NONE);

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(of, save, protectedConstructor, other);
		assertThat(b).isTrue();
	}

}