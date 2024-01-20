package com.mosinsa.product.domain.product;

import com.mosinsa.product.domain.category.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @EmbeddedId
    private ProductId id;

    private String name;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "likes_id")
    private Likes likes;


    public static Product create(String name, Integer price, Category category, long stock) {
        Product product = new Product();
        product.id = ProductId.newId();
        product.name = name;
        product.price = Money.of(price);
        product.category = category;
        product.stock = Stock.of(stock);
        product.likes = Likes.create();
        return product;
    }

    public void likes(String memberId) {
        this.likes.likes(memberId);
    }

    public void increaseStock(long stock) {
        this.stock.increase(stock);
    }

    public void decreaseStock(long stock) {
        this.stock.decrease(stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
