package com.mosinsa.customer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @Column(name = "customer_id")
    private String id;

    @NotBlank
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerGrade grade;

    public Customer(String loginId, String name, String password, String email){
		this.id = UUID.randomUUID().toString();
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public Customer(CustomerGrade grade) {
        this.grade = grade;
    }
}
