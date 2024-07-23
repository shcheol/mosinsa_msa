package com.mosinsa.reaction.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Embeddable
public class ReactionId implements Serializable {

	@Column(name = "reaction_id")
	public String id;

	public String getId() {
		return id;
	}

	protected ReactionId() {
	}

	public static ReactionId newId() {
		return new ReactionId(UUID.randomUUID().toString());
	}


	public static ReactionId of(String id) {
		if (!StringUtils.hasText(id)){
			throw new IllegalArgumentException("invalid Id value");
		}
		return new ReactionId(id);
	}

	private ReactionId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ReactionId that = (ReactionId) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
