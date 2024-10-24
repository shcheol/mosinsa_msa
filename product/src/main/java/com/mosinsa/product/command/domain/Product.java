package com.mosinsa.product.command.domain;

import com.mosinsa.category.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AuditingEntity {

    @EmbeddedId
	@GeneratedValue(generator = "product_id")
	@GenericGenerator(name = "product_id", strategy = "com.mosinsa.product.command.domain.ProductIdGenerator")
    private ProductId id;

    private String name;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "stock_id")
    private Stock stock;


    public static Product create(String name, Integer price, Category category, long stock) {
        Product product = new Product();
        product.name = name;
        product.price = Money.of(price);
        product.category = category;
        product.stock = Stock.of(stock);
        return product;
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
