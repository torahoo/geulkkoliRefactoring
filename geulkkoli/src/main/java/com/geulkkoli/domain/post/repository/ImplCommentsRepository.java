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
    public Comments save(Comments comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public Optional<Comments> findById(Long commentId) {
        Comments findComment = em.find(Comments.class, commentId);
        return Optional.of(findComment);
    }

    @Override
    public List<Comments> findAll() {
        return em.createQuery("select c from Comments c", Comments.class)
                .getResultList();
    }

    @Override
    public void update(Long commentId, Comments updateParam) {
        Comments comment = em.find(Comments.class, commentId);
        comment.changeComments(updateParam.getCommentBody());
    }

    @Override
    public void delete(Long commentId) {
        Comments findComment = em.find(Comments.class, commentId);
        findComment.getPost().getComments().remove(findComment);
        findComment.getUser().getComments().remove(findComment);
        em.remove(findComment);
    }

    public void clear () {
        em.close();
    }
}
