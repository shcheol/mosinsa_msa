package com.shopping.mosinsa.controller;

import com.shopping.mosinsa.entity.Customer;
import com.shopping.mosinsa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> orders(@RequestBody Customer request){

        Customer save = customerRepository.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

}
