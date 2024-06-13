package com.mosinsa.review.command.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDislikes {

	@Id
	private String commentDislikesId;

	private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CommentDislikes that = (CommentDislikes) o;

		return Objects.equals(commentDislikesId, that.commentDislikesId);
	}

	@Override
	public int hashCode() {
		return commentDislikesId != null ? commentDislikesId.hashCode() : 0;
	}
}
