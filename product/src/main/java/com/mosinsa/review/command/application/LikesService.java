package com.mosinsa.review.command.application;

import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.CommentDislikes;
import com.mosinsa.review.command.domain.CommentLikes;
import com.mosinsa.review.infra.jpa.CommentDislikesRepository;
import com.mosinsa.review.infra.jpa.CommentLikesRepository;
import com.mosinsa.review.infra.jpa.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

	private final CommentLikesRepository commentLikesRepository;

	private final CommentDislikesRepository commentDislikesRepository;

	private final CommentRepository commentRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void likesComment(String commentId, String customerId){
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		CommentLikes commentLikes = CommentLikes.create(customerId, comment);
		commentLikesRepository.save(commentLikes);
		comment.likes();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void likesCommentCancel(String commentId, String customerId){
		Comment comment = commentRepository.findById(commentId).orElseThrow();
//		CommentLikes commentLikes = CommentLikes.create(customerId, comment);
		commentLikesRepository.deleteCommentLikesByMemberId(customerId, comment);
		comment.likesCancel();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void dislikesComment(String commentId, String customerId){
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		CommentDislikes commentDislikes = CommentDislikes.create(customerId, comment);
		commentDislikesRepository.save(commentDislikes);
		comment.dislikes();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void dislikesCommentCancel(String commentId, String customerId){
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		CommentDislikes commentDislikes = CommentDislikes.create(customerId, comment);
		commentDislikesRepository.save(commentDislikes);
		comment.dislikesCancel();
	}
}
