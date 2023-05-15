package com.geulkkoli.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1249895496L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final QConfigDate _super = new QConfigDate(this);

    public final SetPath<com.geulkkoli.domain.comment.Comments, com.geulkkoli.domain.comment.QComments> comments = this.<com.geulkkoli.domain.comment.Comments, com.geulkkoli.domain.comment.QComments>createSet("comments", com.geulkkoli.domain.comment.Comments.class, com.geulkkoli.domain.comment.QComments.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final SetPath<com.geulkkoli.domain.favorites.Favorites, com.geulkkoli.domain.favorites.QFavorites> favorites = this.<com.geulkkoli.domain.favorites.Favorites, com.geulkkoli.domain.favorites.QFavorites>createSet("favorites", com.geulkkoli.domain.favorites.Favorites.class, com.geulkkoli.domain.favorites.QFavorites.class, PathInits.DIRECT2);

    public final SetPath<com.geulkkoli.domain.hashtag.HashTags, com.geulkkoli.domain.hashtag.QHashTags> hashTags = this.<com.geulkkoli.domain.hashtag.HashTags, com.geulkkoli.domain.hashtag.QHashTags>createSet("hashTags", com.geulkkoli.domain.hashtag.HashTags.class, com.geulkkoli.domain.hashtag.QHashTags.class, PathInits.DIRECT2);

    public final StringPath imageUploadName = createString("imageUploadName");

    public final StringPath nickName = createString("nickName");

    public final StringPath postBody = createString("postBody");

    public final NumberPath<Integer> postHits = createNumber("postHits", Integer.class);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public final com.geulkkoli.domain.user.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.geulkkoli.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

