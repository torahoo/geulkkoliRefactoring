package com.geulkkoli.domain.follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepository {

    Follow save (Follow follow);
    Optional<Follow> findById (Long followId);
    List<Follow> findAll();
    void delete (Long followId);
}
