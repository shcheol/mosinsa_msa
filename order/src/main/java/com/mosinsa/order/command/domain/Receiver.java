package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.ReceiverDto;
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

    public static Receiver of(ReceiverDto receiverDto) {
        Receiver receiver = new Receiver();
        receiver.name = receiverDto.name();
        receiver.phoneNumber = receiverDto.phoneNumber();
        return receiver;
    }
}
