package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.dto.edit.UserInfoEditDto;
public interface UserRepositoryCustom {


    void edit(Long id, UserInfoEditDto userInfoEditDto);

    void editPassword(Long id, String newPassword);

}
