package com.mosinsa.customer.web.controller.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestCreateCustomer {

    private String loginId;

    private String name;

    private String password;

    @Email
    private String email;
}
