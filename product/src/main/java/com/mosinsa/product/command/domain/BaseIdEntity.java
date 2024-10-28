package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseIdEntity extends AuditingEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BaseIdEntity that)) return false;

		return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}
