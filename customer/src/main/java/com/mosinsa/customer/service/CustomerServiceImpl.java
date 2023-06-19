package com.mosinsa.customer.service;

import com.mosinsa.customer.dto.CustomerDto;
import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.feignclient.OrderServiceClient;
import com.mosinsa.customer.repository.CustomerRepository;
import com.mosinsa.customer.web.controller.request.RequestCreateCustomer;
import com.mosinsa.customer.web.controller.response.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
//        if(repository.findCustomerByLoginId(customer.getLoginId()).isPresent()){
//            throw new IllegalStateException("이미 존재하는 Id입니다.");
//        }
        return repository.findCustomerByLoginId(loginId).isPresent();
    }

    @Override
    public Customer login(String loginId, String password) {

        return repository.findCustomerByLoginId(loginId)
                .filter(customer -> customer.getPassword().equals(password)).orElse(null);

    }

    public Customer findById(Long customerId){
        return repository.findById(customerId).orElse(null);
    }

    public CustomerDto getCustomerDetailsByCustomerId(Long customerId) {
        Customer customer = repository.findById(customerId).orElse(null);
        if (customer == null){
            throw new IllegalStateException("User not found");
        }

//        List<ResponseOrder> orders = getOrdersByRestTemplate(customerId);
        List<ResponseOrder> orders = getOrdersByFeignClient(customerId);
        return new CustomerDto(customer.getId(), customer.getName(), orders);
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
