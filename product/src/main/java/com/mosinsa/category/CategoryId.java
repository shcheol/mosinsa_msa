package com.mosinsa.category;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Embeddable
public class CategoryId implements Serializable {
    @Column(name = "category_id")
    private String id;

    public static CategoryId newId() {
        return new CategoryId(UUID.randomUUID().toString());
    }


    public static CategoryId of(String id) {
        return new CategoryId(id);
    }

    private CategoryId(String id){
		if (!StringUtils.hasText(id)){
			throw new IllegalArgumentException("invalid Id value");
		}
		this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryId categoryId = (CategoryId) o;

        return Objects.equals(id, categoryId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
