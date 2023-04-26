package com.geulkkoli.domain.post;

public interface PostRepositoryCustom {

    void update(Long postId, Post updateParam);

    void delete(Long postId);

}
