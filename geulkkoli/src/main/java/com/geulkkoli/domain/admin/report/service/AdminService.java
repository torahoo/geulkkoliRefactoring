package com.geulkkoli.domain.admin.report.service;

import com.geulkkoli.domain.admin.report.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final ReportRepository reportRepository;

    public AdminService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

}
