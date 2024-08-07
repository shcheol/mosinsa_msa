package com.mosinsa.websocket.redis;

public record WebsocketMessage(DomainType type, String reviewId, String commentId, LikesType likesType,
							   boolean canceled) {

	public static WebsocketMessage reviewLikes(String reviewId, boolean canceled) {
		return new WebsocketMessage(DomainType.REVIEW, reviewId, "", LikesType.LIKES, canceled);
	}

	public static WebsocketMessage reviewDislikes(String reviewId, boolean canceled) {
		return new WebsocketMessage(DomainType.REVIEW, reviewId, "", LikesType.DISLIKES, canceled);
	}

	public static WebsocketMessage commentLikes(String commentId, boolean canceled) {
		return new WebsocketMessage(DomainType.COMMENT, "", commentId, LikesType.LIKES, canceled);
	}

	public static WebsocketMessage commentDislikes(String commentId, boolean canceled) {
		return new WebsocketMessage(DomainType.COMMENT, "", commentId, LikesType.DISLIKES, canceled);
	}
}
