package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.admin.ReportRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.ListDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final ReportRepository reportRepository;

    public AdminServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public void rockUser(Long userId) {
        userRepository.findById(userId).ifPresent(userRepository::save);

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
