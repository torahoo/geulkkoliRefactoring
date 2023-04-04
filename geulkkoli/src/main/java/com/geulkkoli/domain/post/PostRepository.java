package com.geulkkoli.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll();
    void update (Long postNo, Post updateParam);
    void delete (Long postNo);
}
