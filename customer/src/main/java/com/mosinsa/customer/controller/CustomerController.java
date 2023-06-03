package com.mosinsa.customer.controller;

import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Slf4j
@Transactional
@Controller
@RequestMapping
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;
    @GetMapping
    public String home(){
        log.info("welcome");
        return "home";
    }
    @GetMapping("/health")
    @ResponseBody
    public String health(){
        log.info("[{}] controller.health",new Timestamp(System.currentTimeMillis()));
        return "health-check";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute Customer customer){
        return "customers/addCustomerForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "customers/addCustomerForm";
        }

        customerService.join(customer);

        return "redirect:/";
    }

    @PostMapping("/customer")
    public ResponseEntity<Long> createCustomer(@RequestBody Customer request){

        Long customerId = customerService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
    }

//    @PostMapping
//    public ResponseEntity<Customer> orders(@RequestBody Customer request){
//
//        Customer save = customerRepository.save(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(save);
//    }

}
