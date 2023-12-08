package com.mosinsa.customer.ui;

import com.mosinsa.customer.dto.CustomerDto;
import com.mosinsa.customer.application.CustomerService;
import com.mosinsa.customer.ui.request.RequestCreateCustomer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/health")
    @ResponseBody
    public String health(){
        log.info("[{}] controller.health",new Timestamp(System.currentTimeMillis()));
        return "health-check";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody RequestCreateCustomer request){

		String customerId = customerService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerDetails(@PathVariable String customerId, HttpServletRequest request){

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println("header = " + header);

		CustomerDto customerDetails = customerService.getCustomerDetailsByCustomerId(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
    }

	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId){

		customerService.delete(customerId);

		return ResponseEntity.ok().build();
	}

}
