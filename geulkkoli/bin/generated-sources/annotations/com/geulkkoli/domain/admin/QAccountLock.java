package com.geulkkoli.domain.admin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountLock is a Querydsl query type for AccountLock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountLock extends EntityPathBase<AccountLock> {

    private static final long serialVersionUID = 2109538739L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountLock accountLock = new QAccountLock("accountLock");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.geulkkoli.domain.user.QUser lockedUser;

    public final DateTimePath<java.time.LocalDateTime> lockExpirationDate = createDateTime("lockExpirationDate", java.time.LocalDateTime.class);

    public final StringPath reason = createString("reason");

    public QAccountLock(String variable) {
        this(AccountLock.class, forVariable(variable), INITS);
    }

    public QAccountLock(Path<? extends AccountLock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountLock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountLock(PathMetadata metadata, PathInits inits) {
        this(AccountLock.class, metadata, inits);
    }

    public QAccountLock(Class<? extends AccountLock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lockedUser = inits.isInitialized("lockedUser") ? new com.geulkkoli.domain.user.QUser(forProperty("lockedUser"), inits.get("lockedUser")) : null;
    }

}

