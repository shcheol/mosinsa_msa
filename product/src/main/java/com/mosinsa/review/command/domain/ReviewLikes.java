package com.mosinsa.review.command.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLikes {

	@Id
	@Column(name = "review_likes_id")
	private String id;

	private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	public static ReviewLikes create(String memberId, Review review){
		ReviewLikes reviewLikes = new ReviewLikes();
		reviewLikes.id = UUID.randomUUID().toString();
		reviewLikes.memberId = memberId;
		reviewLikes.review = review;
		return reviewLikes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ReviewLikes that = (ReviewLikes) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
