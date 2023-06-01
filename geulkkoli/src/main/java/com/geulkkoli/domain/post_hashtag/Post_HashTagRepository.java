package com.geulkkoli.domain.post_hashtag;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Post_HashTagRepository extends JpaRepository<Post_HashTag, Long> {
    List<Post_HashTag> findAllByHashTag (HashTag hashTagId);
}
