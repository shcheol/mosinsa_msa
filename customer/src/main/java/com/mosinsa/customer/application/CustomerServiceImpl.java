package com.mosinsa.customer.application;

import com.mosinsa.customer.common.ex.CustomerError;
import com.mosinsa.customer.common.ex.CustomerException;
import com.mosinsa.customer.domain.CustomerId;
import com.mosinsa.customer.dto.CustomerDto;
import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.infra.repository.CustomerRepository;
import com.mosinsa.customer.ui.request.CreateCustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository repository;

	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public String join(CreateCustomerRequest request) {

		validateDuplicateCustomer(request.loginId());

		Customer customer = Customer.create(
				request.loginId(),
				passwordEncoder.encode(request.password()),
				request.name(),
				request.email(),
				request.city(),
				request.street(),
				request.zipcode()
		);
		repository.save(customer);
		return customer.getId().getId();
	}

	@Override
	public Customer findByLoginId(String loginId) {
		return repository.findCustomerByLoginId(loginId).orElse(null);
	}


	@Override
	public Customer login(String loginId, String password) {
		return repository.findCustomerByLoginId(loginId)
				.filter(customer -> customer.getCert().getPassword().equals(password))
				.orElseThrow(() -> new CustomerException(CustomerError.WRONG_ID_OR_PASSWORD));
	}

	@Override
	public CustomerDto customerDetails(String customerId) {
		Customer customer = repository.findById(CustomerId.of(customerId))
				.orElseThrow(() -> new CustomerException(CustomerError.CUSTOMER_NOT_FOUND));
		return new CustomerDto(customer);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Customer customer = repository.findCustomerByLoginId(username)
				.orElseThrow(() -> new CustomerException(CustomerError.CUSTOMER_NOT_FOUND));

		return new User(customer.getCert().getLoginId(), customer.getCert().getPassword(),
				true, true, true, true, new ArrayList<>());
	}

	private void validateDuplicateCustomer(String loginId) {
		if (repository.findCustomerByLoginId(loginId).isPresent()) {
			throw new CustomerException(CustomerError.DUPLICATED_LOGINID);
		}
	}

}
