package com.mosinsa.customer.ui.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLogin {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
