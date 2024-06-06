package com.mosinsa.review.command.application;

import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.command.domain.Writer;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void writeReview(WriteReviewRequest request){

        Writer writer = Writer.of(request.writerId(), request.writerName());
        Review review = Review.write(writer, request.productId(), request.content());
        reviewRepository.save(review);
    }
    @Transactional
    public void delete(String reviewId, DeleteReviewRequest request){
        Review review = reviewRepository.findById(ReviewId.of(reviewId))
                .orElseThrow(()->new IllegalArgumentException("not found"));
        review.delete(request.writerId());
    }
}
