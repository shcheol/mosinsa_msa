package com.mosinsa.customer.application;

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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Autowired
	CustomerService customerService;

	@MockBean
	CustomerRepository customerRepository;

	@Autowired
	AdminClient adminClient;

	@TestConfiguration
	static class ConsumerConfig {

		@Bean
		public AdminClient adminClient(KafkaAdmin kafkaAdmin) {
			return AdminClient.create(kafkaAdmin.getConfigurationProperties());
		}
	}

	@Test
	void join() throws ExecutionException, InterruptedException {
		long beforeOffset = getOffset();

		customerService.join(new CreateCustomerRequest(UUID.randomUUID().toString(),
				"password", "name", "email@email.com", "city", "street", "zipcode"));

		Thread.sleep(1000);
		long afterOffset = getOffset();

		assertThat(afterOffset).isEqualTo(beforeOffset + 1);
	}

	public long getOffset() throws ExecutionException, InterruptedException {
		Map<TopicPartition, OffsetSpec> target = new HashMap<>();
		TopicPartition partition = new TopicPartition("mosinsa-customer-create", 0);
		target.put(partition, OffsetSpec.latest());
		ListOffsetsResult listOffsetsResult = adminClient.listOffsets(target);
		return listOffsetsResult.partitionResult(partition).get().offset();
	}

	@Test
	void joinEx() throws ExecutionException, InterruptedException {

		when(customerRepository.save(any()))
				.thenThrow(new RuntimeException());

		long beforeOffset = getOffset();
		System.out.println(beforeOffset);

		assertThrows(RuntimeException.class,
				() -> customerService.join(new CreateCustomerRequest(UUID.randomUUID().toString(),
						"password", "name", "email@email.com", "city", "street", "zipcode")));

		Thread.sleep(1000);
		long afterOffset = getOffset();
		System.out.println(beforeOffset);
		assertThat(afterOffset).isEqualTo(beforeOffset);
	}
}