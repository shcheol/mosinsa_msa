package com.mosinsa.customer.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

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
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

//    @Embedded
//    private Address address;

//    @OneToMany(mappedBy = "customer")
//    private List<Order> orders = new ArrayList<>();
//
//    @OneToMany(mappedBy = "customer")
//    private List<Coupon> coupons = new ArrayList<>();
//
//    @OneToMany(mappedBy = "customer")
//    private List<CustomerCouponEvent> couponEvents = new ArrayList<>();
//
//    public void setCouponEvents(List<CustomerCouponEvent> couponEvents) {
//        this.couponEvents = couponEvents;
//    }


    public Customer(CustomerGrade grade) {
        this.grade = grade;
    }
}
