package com.mosinsa.review.ui;

import com.mosinsa.review.command.application.ReviewService;
import com.mosinsa.review.ui.argumentresolver.CustomerInfo;
import com.mosinsa.review.ui.argumentresolver.Login;
import com.mosinsa.review.ui.reqeust.DeleteCommentRequest;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteCommentRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> writeReview(@RequestBody WriteReviewRequest request) {
        reviewService.writeReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{reviewId}/delete")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") String reviewId,
                                             @RequestBody DeleteReviewRequest request) {
        reviewService.deleteReview(reviewId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<Void> writeComment(@PathVariable("reviewId") String reviewId,
                                             @RequestBody WriteCommentRequest request) {
        reviewService.writeComment(reviewId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/comments/{commentId}/delete")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") String reviewId,
                                             @PathVariable("commentId") String commentId,
                                             @RequestBody DeleteCommentRequest request) {
        reviewService.deleteComment(reviewId, commentId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/comments/{commentId}/likes")
    public ResponseEntity<Void> likesComment(@PathVariable("reviewId") String reviewId,
                                             @PathVariable("commentId") String commentId,
                                             @Login CustomerInfo customerInfo) {
        reviewService.likesComment(reviewId, commentId, customerInfo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/comments/{commentId}/dislikes")
    public ResponseEntity<Void> dislikesComment(@PathVariable("reviewId") String reviewId,
                                                @PathVariable("commentId") String commentId,
                                                @Login CustomerInfo customerInfo) {
        reviewService.dislikesComment(reviewId, commentId, customerInfo);
        return ResponseEntity.ok().build();
    }
}
