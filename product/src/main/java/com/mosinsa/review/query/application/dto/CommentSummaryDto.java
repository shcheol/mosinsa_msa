package com.mosinsa.review.query.application.dto;

import com.mosinsa.review.command.domain.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.time.LocalDateTime;
@Value
public class CommentSummaryDto {
		String commentId;
		String writerId;
		String writer;
		String contents;
		LocalDateTime createdDate;

		Long likesCount;

		Long dislikesCount;

		@QueryProjection
		public CommentSummaryDto(Comment comment) {
			this.commentId = comment.getId();
			this.writerId = comment.getWriter().getWriterId();
			this.writer = comment.getWriter().getName();
			this.contents = comment.isDeleted() ? "삭제된 댓글입니다." : comment.getContents();
			this.createdDate = comment.getCreatedDate();
			this.likesCount = comment.getLikesCount();
			this.dislikesCount = comment.getDislikesCount();
		}
	}