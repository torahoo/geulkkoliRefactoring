package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.Post;

import com.geulkkoli.domain.post.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

    @Modifying
    @Query("update Post p set p.postHits = p.postHits+1 where p.postId=:postId")
    void updateHits(@Param("postId") Long postId);

}
