package com.mosinsa.customer.service;

import com.mosinsa.customer.common.ex.CustomerError;
import com.mosinsa.customer.common.ex.CustomerException;
import com.mosinsa.customer.db.dto.CustomerDto;
import com.mosinsa.customer.db.entity.Customer;
import com.mosinsa.customer.service.feignclient.OrderServiceClient;
import com.mosinsa.customer.db.repository.CustomerRepository;
import com.mosinsa.customer.web.controller.request.RequestCreateCustomer;
import com.mosinsa.customer.web.controller.response.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;

    private final OrderServiceClient orderServiceClient;

    @Transactional
    public Long join(RequestCreateCustomer requestCustomer){

        if(validateDuplicateCustomer(requestCustomer.getLoginId())){
            return null;
        }
        Customer customer = new Customer(requestCustomer.getLoginId(), requestCustomer.getName(), requestCustomer.getPassword(), requestCustomer.getEmail());
        repository.save(customer);
        return customer.getId();
    }

    @Override
    public Long join(Customer customer) {
        if(validateDuplicateCustomer(customer.getLoginId())){
            return null;
        }
        repository.save(customer);
        return customer.getId();
    }

    @Override
    public Customer findByLoginId(String loginId) {
        return repository.findCustomerByLoginId(loginId).orElse(null);
    }

    private boolean validateDuplicateCustomer(String loginId) {
        if(repository.findCustomerByLoginId(loginId).isPresent()){
            throw new CustomerException(CustomerError.DUPLICATED_LOGINID);
        }
        return repository.findCustomerByLoginId(loginId).isPresent();
    }

    @Override
    public Customer login(String loginId, String password) {

        return repository.findCustomerByLoginId(loginId)
                .filter(customer -> customer.getPassword().equals(password)).orElse(null);

    }

	@Override
	public void delete(Long customerId) {
		repository.deleteById(customerId);
	}

	public Customer findById(Long customerId){
        return repository.findById(customerId).orElse(null);
    }

    public CustomerDto getCustomerDetailsByCustomerId(Long customerId) {
        Customer customer = repository.findById(customerId).orElseThrow(() -> new CustomerException(CustomerError.CUSTOMER_NOT_FOUND));
        return new CustomerDto(customer.getId(), customer.getName());
    }

    private List<ResponseOrder> getOrdersByFeignClient(Long customerId) {
        return orderServiceClient.getOrders(customerId);
    }

    private List<ResponseOrder> getOrdersByRestTemplate(Long customerId) {
        String orderUrl = "http://localhost:8000/order-service/%s/orders";
        ResponseEntity<List<ResponseOrder>> response = new RestTemplate().exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ResponseOrder>>() {
        });

        return response.getBody();
    }


}
