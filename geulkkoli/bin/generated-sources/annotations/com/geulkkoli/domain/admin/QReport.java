package com.geulkkoli.domain.admin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = 1719005081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final StringPath reason = createString("reason");

    public final DateTimePath<java.time.LocalDateTime> reportedAt = createDateTime("reportedAt", java.time.LocalDateTime.class);

    public final com.geulkkoli.domain.post.QPost reportedPost;

    public final com.geulkkoli.domain.user.QUser reporter;

    public final NumberPath<Long> reportId = createNumber("reportId", Long.class);

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reportedPost = inits.isInitialized("reportedPost") ? new com.geulkkoli.domain.post.QPost(forProperty("reportedPost"), inits.get("reportedPost")) : null;
        this.reporter = inits.isInitialized("reporter") ? new com.geulkkoli.domain.user.QUser(forProperty("reporter"), inits.get("reporter")) : null;
    }

}

