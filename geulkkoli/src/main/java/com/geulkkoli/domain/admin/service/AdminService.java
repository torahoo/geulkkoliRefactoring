package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.admin.AccountLock;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.topic.Topic;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.admin.DailyTopicDto;
import com.geulkkoli.web.admin.ReportDto;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;

import java.util.List;

public interface AdminService {

    AccountLock lockUser(Long userId, String reason, Long lockDate);

    List<ReportDto> findAllReportedPost();

    User findUserByPostId(Long postId);

    void deletePost(Long postId);

    Post saveNotice(AddDTO post, User user);

    void updateNotice(Long postId, EditDTO updateParam);

    Post findById(Long postId);

    List<DailyTopicDto> findWeeklyTopic();

    public List<Topic> fillTopic(List<Topic> topics);

    public void updateTopic(DailyTopicDto topic);
}
