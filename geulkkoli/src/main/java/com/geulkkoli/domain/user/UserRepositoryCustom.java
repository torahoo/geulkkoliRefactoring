package com.geulkkoli.domain.user;

import com.geulkkoli.web.myPage.dto.edit.UserInfoEditFormDto;
public interface UserRepositoryCustom {


    void edit(Long id, UserInfoEditFormDto userInfoEditFormDto);

    void editPassword(Long id, String newPassword);

}
