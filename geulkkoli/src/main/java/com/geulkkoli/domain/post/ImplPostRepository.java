package com.geulkkoli.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ImplPostRepository implements PostRepository {

    private final EntityManager entityManager;

    @Override
    public Post save(Post post) {
        entityManager.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long postNo) {
        Post post = entityManager.find(Post.class, postNo);
        return Optional.of(post);
    }

    @Override
    public List<Post> findAll() {
        return entityManager
                .createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Override
    public void update(Long postNo, Post updateParam) {
        Post findPost = entityManager.find(Post.class, postNo);
        findPost.changeTitle(updateParam.getTitle());
        findPost.changePostBody(updateParam.getPostBody());
    }

    @Override
    public void delete(Long postNo) {
        Post deletePost = entityManager.find(Post.class, postNo);
        entityManager.remove(deletePost);
    }
}
