package com.geulkkoli.domain.post;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

/**
 * PostRepositoryImpl은 querydsl를 이용하기  위해 만든 클래스입니다.
 * 굳이 이 방식을 쓰지 않아도 되지만 그렇게 할 시 qurydsl 설정을 알기 힘들 수도 있어 조금 예전 방식을 써보았습니다.
 * 이 방식은 querydsl를 이용해 update, delete를 할 수 있게 해줍니다.
 * querydsl을 통해 복잡한 동적 쿼리를 작성하길 추천합니다.
 * 참고: https://jojoldu.tistory.com/372
 * how to use: https://www.baeldung.com/intro-to-querydsl
 * how to user page: https://www.baeldung.com/rest-api-pagination-in-spring
 * 를 참고 해보아도 좋을 거 같습니다.
 */
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
