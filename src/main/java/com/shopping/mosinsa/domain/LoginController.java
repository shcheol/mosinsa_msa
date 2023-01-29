package com.shopping.mosinsa.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/")
    public String home() {
        return "Mosinsa Main Page";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "LoginPage";
    }
}
