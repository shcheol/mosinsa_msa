package com.mosinsa.customer.application;

import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.domain.CustomerId;
import com.mosinsa.customer.infra.repository.CustomerRepository;
import com.mosinsa.customer.ui.request.CreateCustomerRequest;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Sql("classpath:db/test-init.sql")
@SpringBootTest
class CustomerServiceImplTest {

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void join() {

		String loginId = UUID.randomUUID().toString();
		String customerId = customerService.join(new CreateCustomerRequest(loginId,
				"password", "name", "email@email.com", "city", "street", "zipcode"));
		Customer customer = customerRepository.findById(CustomerId.of(customerId)).get();

		assertThat(customer.getCert().getLoginId()).isEqualTo(loginId);
	}

}