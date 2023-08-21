package com.mosinsa.customer.web.controller;

import com.mosinsa.customer.common.ex.CustomerError;
import com.mosinsa.customer.db.dto.CustomerDto;
import com.mosinsa.customer.db.entity.Customer;
import com.mosinsa.customer.service.CustomerService;
import com.mosinsa.customer.web.argumentresolver.Login;
import com.mosinsa.customer.web.controller.request.RequestLogin;
import com.mosinsa.customer.web.controller.request.RequestCreateCustomer;
import com.mosinsa.customer.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Slf4j
@Transactional
@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

/*    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_CUSTOMER, required = false) Long customerId, Model model){

        if(customerId == null){
            return "home";
        }
        Customer loginCustomer = customerService.findById(customerId);
        if(loginCustomer == null){
            return "home";
        }
        model.addAttribute("customer", loginCustomer);

        return "loginHome";
    }*/

    @GetMapping
    public String home(@Login String customerId, Model model){

        if(customerId == null){
            return "home";
        }
		if(customerService.findById(customerId) == null){
            return "home";
        }
        model.addAttribute("customer", customerService.findById(customerId));

        return "loginHome";
    }

    @GetMapping("/health")
    @ResponseBody
    public String health(){
        log.info("[{}] controller.health",new Timestamp(System.currentTimeMillis()));
        return "health-check";
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute Customer customer){
        return "customers/joinCustomerForm";
    }

    @PostMapping("/join")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "customers/joinCustomerForm";
        }

        if(customerService.join(customer) == null){
            bindingResult.addError(new FieldError("customer","loginId",CustomerError.DUPLICATED_LOGINID.getMessage()));
            return "customers/joinCustomerForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute RequestLogin loginForm){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDto> login(@Valid @ModelAttribute RequestLogin requestLogin, BindingResult bindingResult, HttpServletRequest request){
//        if(bindingResult.hasErrors()){
//            return "login/loginForm";
//        }

        Customer customer = customerService.login(requestLogin.getLoginId(), requestLogin.getPassword());
//        if (loginCustomer == null){
//            bindingResult.reject("loginFail", CustomerError.WRONG_ID_OR_PASSWORD.getMessage());
//            return "login/loginForm";
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionConst.LOGIN_CUSTOMER, loginCustomer.getId());

        return ResponseEntity.ok(new CustomerDto(customer.getId(), customer.getName()));
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

    @PostMapping("/customer")
    public ResponseEntity<String> createCustomer(@RequestBody RequestCreateCustomer request){

		String customerId = customerService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerDetails(@PathVariable String customerId){

        CustomerDto customerDetails = customerService.getCustomerDetailsByCustomerId(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
    }

	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId){

		customerService.delete(customerId);

		return ResponseEntity.ok().build();
	}

}
