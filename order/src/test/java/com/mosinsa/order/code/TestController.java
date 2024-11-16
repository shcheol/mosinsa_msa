package com.mosinsa.order.code;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/success")
	public Object loginUser(@Login CustomerInfo customerInfo){
		return customerInfo;
	}

	@GetMapping("/fail")
	public Object loginUser(@Login TestClass customerInfo){
		return customerInfo;
	}

	@GetMapping("/success2")
	public Object loginUserOrGuest(CustomerInfo customerInfo){
		return customerInfo;
	}

	@GetMapping("/fail2")
	public Object loginUserOrGuest(TestClass customerInfo){
		return customerInfo;
	}
}
