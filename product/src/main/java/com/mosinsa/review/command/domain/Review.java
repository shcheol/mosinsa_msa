package com.mosinsa.review.command.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @EmbeddedId
    private ReviewId reviewId;

    @Embedded
    private Writer writer;

    private String productId;

    private String contents;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private Long commentsCount;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Convert(converter = BooleanConverter.class)
    private boolean deleted;

    public static Review of(Writer writer, String productId, String contents) {
        Review review = new Review();
        review.reviewId = ReviewId.newId();
        review.writer = writer;
        review.productId = productId;
        review.contents = contents;
        review.commentsCount = 0L;
        review.createdDate = LocalDateTime.now();
        review.deleted = false;
        return review;
    }

    public void writeComment(Comment comment){
        this.getComments().add(comment);
        this.commentsCount+=1;
    }

    public void deleteComment(){
        this.commentsCount-=1;
    }
    public ReviewId delete(String writerId) {
        if (!writerId.equals(this.writer.getWriterId())){
            throw new IllegalStateException("자신이 작성한 글만 삭제 할 수 있습니다.");
        }
        this.deleted = true;
        return this.reviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }
}
