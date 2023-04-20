package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.edit.EditFormDto;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmail(String userName, String phoneNo);

    Optional<User> findByNickName(String nickName);

    Optional<User> findByPhoneNo(String phoneNo);

    void update(Long id, EditFormDto editFormDto);

    void updatePassword(Long id, String newPassword);

    void delete (Long postId);
}
