package com.mosinsa.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Receiver {

    @Column(name = "receiver_name")
    private String name;

    @Column(name = "receiver_phone")
    private String phoneNumber;

    public static Receiver of(String name, String phoneNumber) {
        Receiver receiver = new Receiver();
        receiver.name = name;
        receiver.phoneNumber = phoneNumber;
        return receiver;
    }
}
