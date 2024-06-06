package com.mosinsa.review.ui.reqeust;

public record WriteReviewRequest(String writerId, String writerName, String productId, String content) {
}
