package com.mosinsa.order.controller.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class RequestCreateCustomer {

    private String loginId;

    private String name;

    private String password;

    @Email
    private String email;
}
