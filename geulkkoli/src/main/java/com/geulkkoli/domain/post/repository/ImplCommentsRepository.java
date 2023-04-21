package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.Comments;
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
public class ImplCommentsRepository implements CommentsRepository {

    private final EntityManager em;

    @Override
    public Comments save(Comments comments) {
        em.persist(comments);
        return comments;
    }

    @Override
    public Optional<Comments> findById(Long commentsId) {
        Comments findComments = em.find(Comments.class, commentsId);
        return Optional.of(findComments);
    }

    @Override
    public List<Comments> findAll() {
        return em.createQuery("select c from Comments c", Comments.class)
                .getResultList();
    }

    @Override
    public void update(Long commentsId, Comments updateParam) {
        Comments comments = em.find(Comments.class, commentsId);
        comments.changeComments(updateParam.getCommentBody());
    }

    @Override
    public void delete(Long commentsId) {
        Comments delete = em.find(Comments.class, commentsId);
        em.remove(delete);
    }

    public void clear () {
        em.close();
    }
}
