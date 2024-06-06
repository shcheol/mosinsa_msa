package com.mosinsa.review.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writer {

    @Column(name = "writer_id")
    private String writerId;

    @Column(name = "writer_name")
    private String name;

    public static Writer of(String writerId, String name){
        Writer writer = new Writer();
        writer.writerId = writerId;
        writer.name = name;
        return writer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Writer writer)) return false;
        return Objects.equals(writerId, writer.writerId) && Objects.equals(name, writer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writerId, name);
    }
}
