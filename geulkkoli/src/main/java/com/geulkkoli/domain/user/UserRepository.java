package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.edit.EditPasswordForm;
import com.geulkkoli.web.user.edit.EditUpdateForm;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);

    Optional<User> findByPhoneNo(String phoneNo);

    void update(User user, EditUpdateForm editUpdateForm);

    void updatePassword(User user, EditPasswordForm form);
}
