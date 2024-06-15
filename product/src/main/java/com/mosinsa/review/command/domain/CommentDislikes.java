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
public class CommentDislikes {

	@Id
	@Column(name = "comment_dislike_id")
	private String id;

	private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	public static CommentDislikes create(String memberId, Comment comment){
		CommentDislikes commentDislikes = new CommentDislikes();
		commentDislikes.id = UUID.randomUUID().toString();
		commentDislikes.memberId = memberId;
		commentDislikes.comment = comment;
		return commentDislikes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CommentDislikes that = (CommentDislikes) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
