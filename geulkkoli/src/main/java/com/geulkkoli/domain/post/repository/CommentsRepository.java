package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository {
    Comments save (Comments comment);
    Optional<Comments> findById (Long commentId);
    List<Comments> findAll();
    void update (Long commentId, Comments updateParam);
    void delete (Long commentId);
}
