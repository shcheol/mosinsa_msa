package com.mosinsa.customer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String loginId;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerGrade grade;

    @Embedded
    private Address address;

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
