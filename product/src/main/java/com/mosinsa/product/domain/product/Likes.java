package com.mosinsa.product.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

	@Id
	private LikesId id;

	private Integer total;

	@OneToMany(mappedBy = "likes", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private final Set<LikesMember> likesMember = new HashSet<>();


	public static Likes of(String productId, String memberId) {
        Likes likes = new Likes();
		likes.id = LikesId.newId();
		likes.total = likes.likesMember.size();
        return likes;
    }

}
