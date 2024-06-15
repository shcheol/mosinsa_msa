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
public class CommentLikes {

	@Id
	@Column(name = "comment_likes_id")
	private String id;

	private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	public static CommentLikes create(String memberId, Comment comment){
		CommentLikes commentLikes = new CommentLikes();
		commentLikes.id = UUID.randomUUID().toString();
		commentLikes.memberId = memberId;
		commentLikes.comment = comment;
		return commentLikes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CommentLikes that = (CommentLikes) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
