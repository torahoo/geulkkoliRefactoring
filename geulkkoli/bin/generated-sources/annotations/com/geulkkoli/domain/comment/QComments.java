package com.geulkkoli.domain.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComments is a Querydsl query type for Comments
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComments extends EntityPathBase<Comments> {

    private static final long serialVersionUID = -308625271L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComments comments = new QComments("comments");

    public final com.geulkkoli.domain.post.QConfigDate _super = new com.geulkkoli.domain.post.QConfigDate(this);

    public final StringPath commentBody = createString("commentBody");

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final com.geulkkoli.domain.post.QPost post;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public final com.geulkkoli.domain.user.QUser user;

    public QComments(String variable) {
        this(Comments.class, forVariable(variable), INITS);
    }

    public QComments(Path<? extends Comments> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComments(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComments(PathMetadata metadata, PathInits inits) {
        this(Comments.class, metadata, inits);
    }

    public QComments(Class<? extends Comments> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.geulkkoli.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.geulkkoli.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

