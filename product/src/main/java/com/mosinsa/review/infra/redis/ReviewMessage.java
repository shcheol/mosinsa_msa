package com.mosinsa.review.infra.redis;

public record ReviewMessage(DomainType type, String reviewId, String commentId, LikesType likesType, boolean canceled) {

    public static ReviewMessage reviewLikes(String reviewId, boolean canceled) {
        return new ReviewMessage(DomainType.REVIEW, reviewId, "", LikesType.LIKES, canceled);
    }

    public static ReviewMessage reviewDislikes(String reviewId, boolean canceled) {
        return new ReviewMessage(DomainType.REVIEW, reviewId, "", LikesType.DISLIKES, canceled);
    }

    public static ReviewMessage commentLikes(String reviewId, String commentId, boolean canceled) {
        return new ReviewMessage(DomainType.COMMENT, reviewId, commentId, LikesType.LIKES, canceled);
    }

    public static ReviewMessage commentDislikes(String reviewId, String commentId, boolean canceled) {
        return new ReviewMessage(DomainType.COMMENT, reviewId, commentId, LikesType.DISLIKES, canceled);
    }
}
