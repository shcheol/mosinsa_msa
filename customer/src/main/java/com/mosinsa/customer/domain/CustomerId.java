package com.mosinsa.customer.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class CustomerId implements Serializable {

	@Column(name = "customer_id")
	private String id;

	public static CustomerId newId() {
		return new CustomerId(UUID.randomUUID().toString());
	}

	public static CustomerId of(String id) {
		return new CustomerId(id);
	}

	private CustomerId(String id){
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CustomerId orderId = (CustomerId) o;

		return Objects.equals(id, orderId.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}
