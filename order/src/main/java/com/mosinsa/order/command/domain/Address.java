package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.AddressDto;
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
public class Address {

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    public static Address of(AddressDto addressDto){
        Address address = new Address();
        address.zipCode = addressDto.zipCode();
        address.address1 = addressDto.address1();
        address.address2 = addressDto.address2();
        return address;
    }
}
