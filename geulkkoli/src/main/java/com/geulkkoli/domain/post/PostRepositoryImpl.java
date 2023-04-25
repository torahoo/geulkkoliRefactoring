package com.geulkkoli.domain.post;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public void update(Long postId, Post updateParam) {
        QPost post = QPost.post;

        queryFactory.update(post)
                .set(post.title, updateParam.getTitle())
                .set(post.postBody, updateParam.getPostBody())
                .where(post.postId.eq(postId))
                .execute();
    }

    @Override
    public void delete(Long postId) {
        QPost post = QPost.post;

        queryFactory.delete(post)
                .where(post.postId.eq(postId))
                .execute();
    }

}
