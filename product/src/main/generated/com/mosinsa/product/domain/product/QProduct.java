package com.mosinsa.product.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -307715844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.mosinsa.product.domain.category.QCategory category;

    public final QProductId id;

    public final SetPath<Likes, QLikes> likes = this.<Likes, QLikes>createSet("likes", Likes.class, QLikes.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final QMoney price;

    public final QStock stock;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.mosinsa.product.domain.category.QCategory(forProperty("category"), inits.get("category")) : null;
        this.id = inits.isInitialized("id") ? new QProductId(forProperty("id")) : null;
        this.price = inits.isInitialized("price") ? new QMoney(forProperty("price")) : null;
        this.stock = inits.isInitialized("stock") ? new QStock(forProperty("stock")) : null;
    }

}
