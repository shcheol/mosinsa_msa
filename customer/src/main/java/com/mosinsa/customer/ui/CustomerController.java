package com.mosinsa.customer.ui;

import com.mosinsa.customer.application.CustomerService;
import com.mosinsa.customer.application.dto.CustomerDto;
import com.mosinsa.customer.ui.request.CreateCustomerRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerService customerService;

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}

	@PostMapping("/customers")
	public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerRequest request) {

		String customerId = customerService.join(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
	}

	@GetMapping("/customers/{customerId}")
	public ResponseEntity<CustomerDto> customerDetails(@PathVariable String customerId, HttpServletRequest request) {

		log.debug("header: {}", request.getHeader(HttpHeaders.AUTHORIZATION));

		CustomerDto customerDetails = customerService.customerDetails(customerId);

		return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
	}

}
