package com.mosinsa.product.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductId is a Querydsl query type for ProductId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProductId extends BeanPath<ProductId> {

    private static final long serialVersionUID = 637819703L;

    public static final QProductId productId = new QProductId("productId");

    public final StringPath id = createString("id");

    public QProductId(String variable) {
        super(ProductId.class, forVariable(variable));
    }

    public QProductId(Path<? extends ProductId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductId(PathMetadata metadata) {
        super(ProductId.class, metadata);
    }

}

