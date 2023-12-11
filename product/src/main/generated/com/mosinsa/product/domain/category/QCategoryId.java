package com.mosinsa.product.domain.category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryId is a Querydsl query type for CategoryId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCategoryId extends BeanPath<CategoryId> {

    private static final long serialVersionUID = 923212277L;

    public static final QCategoryId categoryId = new QCategoryId("categoryId");

    public final StringPath id = createString("id");

    public QCategoryId(String variable) {
        super(CategoryId.class, forVariable(variable));
    }

    public QCategoryId(Path<? extends CategoryId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryId(PathMetadata metadata) {
        super(CategoryId.class, metadata);
    }

}

