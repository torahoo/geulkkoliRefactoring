package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.admin.AccountLock;
import com.geulkkoli.domain.admin.AccountLockRepository;
import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.admin.ReportRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.ListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final ReportRepository reportRepository;


    private final AccountLockRepository accountLockRepository;

    public AdminServiceImpl(ReportRepository reportRepository, UserRepository userRepository, AccountLockRepository accountLockRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.accountLockRepository = accountLockRepository;
    }

    public AdminServiceImpl() {
    }

    public void rockUser(Long userId, String reason) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        AccountLock accountLock = AccountLock.of(user, reason, LocalDateTime.now().plusDays(2));
        user.rock(accountLock);
        accountLockRepository.save(accountLock);
    }

    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }

    public List<ListDTO> findAllReportedPost() {
        List<Report> allPost = reportRepository.findAll();
        List<ListDTO> listDTOs = new ArrayList<>();

        for (Report report : allPost) {
            Post post = report.getReportedPost();
            listDTOs.add(ListDTO.toDTO(post));
        }

        return listDTOs;
    }
}
