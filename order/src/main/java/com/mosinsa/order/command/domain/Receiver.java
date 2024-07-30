package com.mosinsa.order.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Receiver receiver)) return false;
		return Objects.equals(name, receiver.name) && Objects.equals(phoneNumber, receiver.phoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, phoneNumber);
	}
}
