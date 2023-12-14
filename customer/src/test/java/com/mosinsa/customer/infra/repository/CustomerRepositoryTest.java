package com.mosinsa.customer.infra.repository;

import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.domain.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomerRepositoryTest {

	@Autowired
	CustomerRepository repository;

	@Test
	void create(){
		Customer customer = Customer.create(
				"loginId",
				"password",
				"name",
				"email@email.com",
				"city", "street", "zipcode");
		Customer saveCustomer = repository.save(customer);
		assertThat(customer).isEqualTo(saveCustomer);
	}

	@Test
	void findByLoginId(){
		Customer loginId = repository.findCustomerByLoginId("loginId1").get();
		Customer customerId = repository.findById(CustomerId.of("customerId1")).get();
		assertThat(loginId).isEqualTo(customerId);
	}





}