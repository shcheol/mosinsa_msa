package com.mosinsa.review.command.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikes {

	@Id
	private String commentLikesId;

	private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CommentLikes that = (CommentLikes) o;

		return Objects.equals(commentLikesId, that.commentLikesId);
	}

	@Override
	public int hashCode() {
		return commentLikesId != null ? commentLikesId.hashCode() : 0;
	}
}
