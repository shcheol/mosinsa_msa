package com.mosinsa.customer.application;

import com.mosinsa.customer.common.ex.CustomerError;
import com.mosinsa.customer.common.ex.CustomerException;
import com.mosinsa.customer.domain.CustomerId;
import com.mosinsa.customer.dto.CustomerDto;
import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.infra.kafka.CustomerCreatedEvent;
import com.mosinsa.customer.infra.repository.CustomerRepository;
import com.mosinsa.customer.ui.request.CreateCustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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
    public String joinV2(CreateCustomerRequest request) {
        validateDuplicateCustomer(request.loginId());

        Customer customer = Customer.createV2(
                request.loginId(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.email(),
                request.city(),
                request.street(),
                request.zipcode()
        );
        repository.save(customer);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(new URI("http://152.67.205.195:8000/coupon-service/coupons"),
                    new CustomerCreatedEvent(customer.getId().getId()), CustomerCreatedEvent.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
