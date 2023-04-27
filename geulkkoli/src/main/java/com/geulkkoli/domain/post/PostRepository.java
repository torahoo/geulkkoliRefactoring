package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.Post;

import com.geulkkoli.domain.post.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

}
