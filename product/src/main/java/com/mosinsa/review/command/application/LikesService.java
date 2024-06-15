package com.mosinsa.review.command.application;

import com.mosinsa.review.infra.jpa.CommentDislikesRepository;
import com.mosinsa.review.infra.jpa.CommentLikesRepository;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

	private final CommentLikesRepository commentLikesRepository;

	private final CommentDislikesRepository commentDislikesRepository;

	private final ReviewRepository reviewRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void likesComment(String commentId){


	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void dislikesComment(String commentId){

	}
}
