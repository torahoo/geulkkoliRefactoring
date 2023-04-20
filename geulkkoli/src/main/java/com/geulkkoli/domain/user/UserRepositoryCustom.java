package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.edit.EditFormDto;
public interface UserRepositoryCustom {


    void update(Long id, EditFormDto editFormDto);

    void updatePassword(Long id, String newPassword);

}
