package com.geulkkoli.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConfigDate is a Querydsl query type for ConfigDate
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QConfigDate extends EntityPathBase<ConfigDate> {

    private static final long serialVersionUID = -2113510472L;

    public static final QConfigDate configDate = new QConfigDate("configDate");

    public final StringPath createdAt = createString("createdAt");

    public final StringPath updatedAt = createString("updatedAt");

    public QConfigDate(String variable) {
        super(ConfigDate.class, forVariable(variable));
    }

    public QConfigDate(Path<? extends ConfigDate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConfigDate(PathMetadata metadata) {
        super(ConfigDate.class, metadata);
    }

}

