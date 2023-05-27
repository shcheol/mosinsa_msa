package com.mosinsa.customer.controller;

import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Slf4j
@Transactional
@RestController
@RequestMapping("/customer-service")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping("/health")
    public String health(){
        log.info("[{}] controller.health",new Timestamp(System.currentTimeMillis()));
        return "health-check";
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer request){

        Customer save = customerRepository.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PostMapping
    public ResponseEntity<Customer> orders(@RequestBody Customer request){

        Customer save = customerRepository.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

}
