package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository {
    Comments save (Comments comments);
    Optional<Comments> findById (Long commentsId);
    List<Comments> findAll();
    void update (Long commentsId, Comments updateParam);
    void delete (Long commentsId);
}
