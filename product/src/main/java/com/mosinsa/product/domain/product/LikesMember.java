package com.mosinsa.product.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesMember {

	@Id
	@Column(name = "likes_member_id")
	private String id;
	private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "likes_id")
	private Likes likes;

	public static LikesMember create(String memberId, Likes likes){
		LikesMember likesMember = new LikesMember();
		likesMember.id = UUID.randomUUID().toString();
		likesMember.memberId = memberId;
		likesMember.likes = likes;
		return likesMember;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LikesMember id = (LikesMember) o;
		return Objects.equals(this.id, id.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
