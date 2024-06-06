package com.mosinsa.review.ui;

import com.mosinsa.review.command.application.ReviewService;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write")
    public ResponseEntity<Void> writeReview(@RequestBody WriteReviewRequest request) {
        reviewService.writeReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{reviewId}/delete")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") String reviewId, @RequestBody DeleteReviewRequest request) {
        reviewService.delete(reviewId, request);
        return ResponseEntity.ok().build();
    }
}
