package com.geulkkoli.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -413391064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final SetPath<com.geulkkoli.domain.admin.AccountLock, com.geulkkoli.domain.admin.QAccountLock> accountLocks = this.<com.geulkkoli.domain.admin.AccountLock, com.geulkkoli.domain.admin.QAccountLock>createSet("accountLocks", com.geulkkoli.domain.admin.AccountLock.class, com.geulkkoli.domain.admin.QAccountLock.class, PathInits.DIRECT2);

    public final SetPath<com.geulkkoli.domain.comment.Comments, com.geulkkoli.domain.comment.QComments> comments = this.<com.geulkkoli.domain.comment.Comments, com.geulkkoli.domain.comment.QComments>createSet("comments", com.geulkkoli.domain.comment.Comments.class, com.geulkkoli.domain.comment.QComments.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final SetPath<com.geulkkoli.domain.favorites.Favorites, com.geulkkoli.domain.favorites.QFavorites> favorites = this.<com.geulkkoli.domain.favorites.Favorites, com.geulkkoli.domain.favorites.QFavorites>createSet("favorites", com.geulkkoli.domain.favorites.Favorites.class, com.geulkkoli.domain.favorites.QFavorites.class, PathInits.DIRECT2);

    public final StringPath gender = createString("gender");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath phoneNo = createString("phoneNo");

    public final SetPath<com.geulkkoli.domain.post.Post, com.geulkkoli.domain.post.QPost> posts = this.<com.geulkkoli.domain.post.Post, com.geulkkoli.domain.post.QPost>createSet("posts", com.geulkkoli.domain.post.Post.class, com.geulkkoli.domain.post.QPost.class, PathInits.DIRECT2);

    public final SetPath<com.geulkkoli.domain.admin.Report, com.geulkkoli.domain.admin.QReport> reports = this.<com.geulkkoli.domain.admin.Report, com.geulkkoli.domain.admin.QReport>createSet("reports", com.geulkkoli.domain.admin.Report.class, com.geulkkoli.domain.admin.QReport.class, PathInits.DIRECT2);

    public final com.geulkkoli.application.security.QRoleEntity role;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.role = inits.isInitialized("role") ? new com.geulkkoli.application.security.QRoleEntity(forProperty("role")) : null;
    }

}

