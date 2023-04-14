package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.edit.EditPasswordForm;
import com.geulkkoli.web.user.edit.EditForm;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);

    Optional<User> findByPhoneNo(String phoneNo);

    void update(Long id, EditForm editForm);

    void updatePassword(Long id, EditPasswordForm form);

    void delete (Long postId);
}
