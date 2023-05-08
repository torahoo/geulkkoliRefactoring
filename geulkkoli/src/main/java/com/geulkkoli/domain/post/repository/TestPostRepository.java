package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Post save(Post post);
}
