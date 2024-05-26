package com.mosinsa.product.domain.product;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
		if (o == null || getClass() != o.getClass()) return false;

		Likes likes = (Likes) o;
		return Objects.equals(this.id, likes.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
