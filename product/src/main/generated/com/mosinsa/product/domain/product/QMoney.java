package com.mosinsa.product.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMoney is a Querydsl query type for Money
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMoney extends BeanPath<Money> {

    private static final long serialVersionUID = -1759603699L;

    public static final QMoney money = new QMoney("money");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QMoney(String variable) {
        super(Money.class, forVariable(variable));
    }

    public QMoney(Path<? extends Money> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMoney(PathMetadata metadata) {
        super(Money.class, metadata);
    }

}

