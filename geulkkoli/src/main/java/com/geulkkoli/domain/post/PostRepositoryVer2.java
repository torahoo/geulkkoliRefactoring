package com.geulkkoli.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryVer2 extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);

    List<Post> findAll();
}
