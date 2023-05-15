package com.geulkkoli.domain.hashtag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashTags is a Querydsl query type for HashTags
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashTags extends EntityPathBase<HashTags> {

    private static final long serialVersionUID = -1226436081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHashTags hashTags = new QHashTags("hashTags");

    public final StringPath hashTagName = createString("hashTagName");

    public final NumberPath<Long> hashTagsId = createNumber("hashTagsId", Long.class);

    public final com.geulkkoli.domain.post.QPost post;

    public QHashTags(String variable) {
        this(HashTags.class, forVariable(variable), INITS);
    }

    public QHashTags(Path<? extends HashTags> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHashTags(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHashTags(PathMetadata metadata, PathInits inits) {
        this(HashTags.class, metadata, inits);
    }

    public QHashTags(Class<? extends HashTags> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.geulkkoli.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

