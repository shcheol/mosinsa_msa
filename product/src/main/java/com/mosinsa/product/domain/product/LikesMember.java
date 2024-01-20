package com.mosinsa.product.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

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

	@ManyToOne
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
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		LikesMember that = (LikesMember) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
