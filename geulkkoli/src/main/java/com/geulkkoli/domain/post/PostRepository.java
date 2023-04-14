package com.geulkkoli.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long postId);
    List<Post> findAll();
    void update (Long postId, Post updateParam);
    void delete (Long postId);


}
