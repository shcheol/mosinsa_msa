package com.mosinsa.review.command.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "comment_id")
    private String id;

    @Embedded
    private Writer writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String contents;

    private LocalDateTime createdDate;

    @Convert(converter = BooleanConverter.class)
    @ColumnDefault(value = "N")
    private boolean deleted;

    public static Comment of(Writer writer, Review review, String contents) {
        Comment comment = new Comment();
        comment.id = UUID.randomUUID().toString();
        comment.writer = writer;
        comment.contents = contents;
        comment.createdDate = LocalDateTime.now();
        comment.deleted = false;
        comment.setReview(review);

        return comment;
    }

    private void setReview(Review review){
        this.review = review;
        this.review.getComments().add(this);
    }

    public String delete(String writerId) {
        if (!writerId.equals(this.writer.getWriterId())){
            throw new IllegalStateException("자신이 작성한 글만 삭제 할 수 있습니다.");
        }
        this.deleted = true;
        return this.id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
