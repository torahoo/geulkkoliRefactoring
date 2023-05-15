package com.geulkkoli.application.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleEntity is a Querydsl query type for RoleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoleEntity extends EntityPathBase<RoleEntity> {

    private static final long serialVersionUID = -1521921595L;

    public static final QRoleEntity roleEntity = new QRoleEntity("roleEntity");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public final SetPath<com.geulkkoli.domain.user.User, com.geulkkoli.domain.user.QUser> users = this.<com.geulkkoli.domain.user.User, com.geulkkoli.domain.user.QUser>createSet("users", com.geulkkoli.domain.user.User.class, com.geulkkoli.domain.user.QUser.class, PathInits.DIRECT2);

    public QRoleEntity(String variable) {
        super(RoleEntity.class, forVariable(variable));
    }

    public QRoleEntity(Path<? extends RoleEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoleEntity(PathMetadata metadata) {
        super(RoleEntity.class, metadata);
    }

}

