package com.mosinsa.customer.controller;

import com.mosinsa.customer.session.SessionConst;
import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.service.CustomerServiceImpl;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Transactional
@Controller
@RequestMapping
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;
    @GetMapping("/")
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

        if(customerService.join(customer) == null){
            bindingResult.addError(new FieldError("customer","loginId","이미 존재하는 ID입니다."));
            return "customers/addCustomerForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Customer loginCustomer = customerService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginCustomer == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_CUSTOMER, loginCustomer.getId());

        return "redirect:/";
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
