package com.geulkkoli.infrastructure;

import com.geulkkoli.domain.post.Post;

import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findById(Long id);

}
