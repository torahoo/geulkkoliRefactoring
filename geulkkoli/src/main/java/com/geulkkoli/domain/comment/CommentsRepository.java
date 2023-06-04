package com.geulkkoli.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    public Set<Comments> findAllByUser_UserId(Long userId);
}
