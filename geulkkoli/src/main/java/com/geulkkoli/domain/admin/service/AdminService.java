package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.user.User;

public interface AdminService {

    void rockUser(Long userId);

    User findUser(long id);

    Object findAllReportedPost();
}
