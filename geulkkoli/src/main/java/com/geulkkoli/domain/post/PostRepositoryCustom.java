package com.geulkkoli.domain.post;

import org.springframework.stereotype.Repository;




public interface PostRepositoryCustom {

    void update(Long postId, Post updateParam);

    void delete(Long postId);

}
