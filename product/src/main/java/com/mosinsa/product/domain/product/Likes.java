package com.mosinsa.product.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

	@EmbeddedId
	private LikesId id;

	private int total;

	public static Likes create() {
		Likes likes = new Likes();
		likes.id = LikesId.newId();
		return likes;
	}

	public void likes() {
		this.total += 1;
	}

	public void likesCancel() {
		this.total -= 1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Likes likes = (Likes) o;
		return id != null && Objects.equals(id, likes.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
