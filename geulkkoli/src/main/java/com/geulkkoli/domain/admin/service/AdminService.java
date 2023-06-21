package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.admin.ReportDto;

import java.util.List;

public interface AdminService {

    void lockUser(Long userId, String reason, Long lockDate);

    List<ReportDto> findAllReportedPost();

    User findUserByPostId(Long postId);

    void deletePost(Long postId);


}
