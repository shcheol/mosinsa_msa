package com.mosinsa.product.domain.product;

import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.ui.request.ProductAddRequest;
import com.mosinsa.product.ui.request.ProductUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @EmbeddedId
    @Column(name = "product_id")
    private ProductId id;

    private String name;

    @Embedded
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    @ElementCollection
    @CollectionTable(name = "likes",
            joinColumns = @JoinColumn(name = "product_id")
    )
    private final Set<Likes> likes = new HashSet<>();


    public static Product create(String name, Integer price, String category, long stock){
        Product product = new Product();
        product.id = ProductId.newId();
        product.name = name;
        product.price = Money.of(price);
        product.category = Category.of(category);
        product.stock = Stock.of(stock);
        return product;
    }

    public Product(ProductAddRequest request) {

        this.id = ProductId.newId();
        this.name = request.getName();
        this.price = Money.of(request.getPrice());
        this.stock = Stock.of(request.getStock());
    }

    public void change(ProductUpdateRequest request) {

        this.name = request.getName();
        this.price = Money.of(request.getPrice());
        this.stock = Stock.of(request.getStock());
    }

    public long totalLikes() {
        return this.likes.size();
    }

    public void likes(String memberId) {
        Likes of = Likes.of(memberId);
        if (this.likes.contains(of)){
            this.likes.remove(of);
        }else{
            this.likes.add(of);
        }
    }

    public void addStock(int stock) {
        this.stock.increase(stock);
    }

    public void removeStock(int stock) {
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
