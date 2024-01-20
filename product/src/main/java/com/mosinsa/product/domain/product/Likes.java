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

	@OneToMany(mappedBy = "likes", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private final Set<LikesMember> likesMember = new HashSet<>();

	public static Likes create() {
        Likes likes = new Likes();
		likes.id = LikesId.newId();
        return likes;
    }

	public void likes(String memberId){
		LikesMember member = likesMember.stream().filter(lm -> lm.getMemberId().equals(memberId))
				.findFirst().orElse(LikesMember.create(memberId, this));

		if(!likesMember.add(member)){
			this.likesMember.remove(member);
		}
		this.total = likesMember.size();
	}
	public void likes(LikesMember member){
		if(!likesMember.add(member)){
			this.likesMember.remove(member);
		}
		this.total = likesMember.size();
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
