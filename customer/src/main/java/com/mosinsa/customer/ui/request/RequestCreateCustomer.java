package com.mosinsa.customer.ui.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestCreateCustomer {

    private String loginId;

    private String name;

    private String password;

    @Email
    private String email;
}
