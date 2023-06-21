package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.admin.AccountLockRepository;
import com.geulkkoli.domain.admin.ReportRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.admin.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final ReportRepository reportRepository;

    private final AccountLockRepository accountLockRepository;

    public void lockUser(Long userId, String reason, Long lockDate){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        LocalDateTime lockUntil = LocalDateTime.now().plusDays(lockDate);
        accountLockRepository.save(user.lock(reason, lockUntil));
    }

    //신고받은 게시글 조회
    public List<ReportDto> findAllReportedPost() {
        List<Post> allPost = reportRepository.findDistinctByReportedPost();
        List<ReportDto> reportDtoList = new ArrayList<>();

        for (Post post : allPost) {
            reportDtoList.add(ReportDto.toDto(post,reportRepository.countByReportedPost(post)));
        }

        return reportDtoList;
    }

    //게시글 작성자 조회
    public User findUserByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return post.getUser();
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("no such postId :" + postId));
        postRepository.delete(post);
    }
}
