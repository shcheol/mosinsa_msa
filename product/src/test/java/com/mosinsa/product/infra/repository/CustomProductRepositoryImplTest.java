package com.mosinsa.product.infra.repository;

import com.mosinsa.product.application.dto.ProductQueryDto;
import com.mosinsa.product.ui.request.OrderEnum;
import com.mosinsa.product.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomProductRepositoryImplTest {

	@Autowired
	ProductRepository repository;

//	@Test
	void test(){

		SearchCondition searchCondition = new SearchCondition(OrderEnum.ASC, OrderEnum.ASC, OrderEnum.ASC, "categoryId1");
		Page<ProductQueryDto> byCondition = repository.findByCondition(searchCondition, PageRequest.of(0,3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);


	}


}