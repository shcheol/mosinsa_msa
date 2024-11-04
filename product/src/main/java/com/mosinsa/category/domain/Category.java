package com.mosinsa.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

	@EmbeddedId
	private CategoryId id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private List<Category> children = new ArrayList<>();

	public static Category of(String name) {
		return Category.of(name, null);
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf(){
		return children.isEmpty();
	}

	public static Category of(String name, Category parent) {
		Category category = new Category();
		category.id = CategoryId.newId();
		category.name = name;
		category.parent = parent;
		if (parent != null) {
			parent.children.add(category);
		}
		return category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Category category = (Category) o;
		return id != null && Objects.equals(id, category.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
