package com.geulkkoli.domain.post;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.post.Post;

import com.geulkkoli.domain.post.PostRepositoryCustom;
import com.geulkkoli.domain.post_hashtag.Post_HashTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

    Page<Post> findAll(Pageable pageable);

    @Modifying
    @Query("update Post p set p.postHits = p.postHits+1 where p.postId=:postId")
    void updateHits(@Param("postId") Long postId);

    Page<Post> findPostsByTitleContaining(Pageable pageable, String searchWords);
    Page<Post> findPostsByNickNameContaining(Pageable pageable, String searchWords);
    Page<Post> findPostsByPostBodyContaining(Pageable pageable, String searchWords);

    Page<Post> findPostsByPostHashTagsContainingAndTitleContaining(Pageable pageable, String searchWords, Post_HashTag tag);
    Page<Post> findPostsByPostHashTagsContainingAndNickNameContaining(Pageable pageable, String searchWords, Post_HashTag tag);
    Page<Post> findPostsByPostHashTagsContainingAndPostBodyContaining(Pageable pageable, String searchWords, Post_HashTag tag);
}
