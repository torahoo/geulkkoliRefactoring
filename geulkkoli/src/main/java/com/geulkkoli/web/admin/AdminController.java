package com.geulkkoli.web.admin;

import com.geulkkoli.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/") // 어드민 기본 페이지 링크
    public String adminIndex(Model model) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);
        List<DailyThemeDto> data = new ArrayList<>();
        data.add(new DailyThemeDto(startDate.toString(), "꽃"));
        data.add(new DailyThemeDto(startDate.plusDays(2).toString(), "이"));
        data.add(new DailyThemeDto(startDate.plusDays(3).toString(), "피"));
        data.add(new DailyThemeDto(startDate.plusDays(4).toString(), "는"));
        data.add(new DailyThemeDto(startDate.plusDays(5).toString(), "계"));
        data.add(new DailyThemeDto(startDate.plusDays(6).toString(), "절"));
        data.add(new DailyThemeDto(startDate.plusDays(7).toString(), "엔"));
        model.addAttribute("data", data);

        return "/admin/adminIndex";
    }

    @ResponseBody
    @PostMapping("/calendar/update")
    public ResponseEntity<Void> updateTheme(@RequestBody DailyThemeDto dailyThemeDto) {
        log.info("date : {}, theme : {}", dailyThemeDto.getDate(), dailyThemeDto.getTheme());
//        LocalDate localDate = LocalDate.parse(dailyThemeDto.getDate(), DateTimeFormatter.BASIC_ISO_DATE);
//        calendarService.updateTheme(localDate, dailyThemeDto.getTheme());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reportedPostList") //신고받은 게시물 링크
    public String reportedPostList(Model model){
        model.addAttribute("list", adminService.findAllReportedPost());
        return "/admin/reportedPostList";
    }

    //lock user with spring security
    @ResponseBody
    @PostMapping("/lockUser")
    public String lockUser(@RequestBody userLockDto userLockDto){
        log.info("userId : {}, reason : {}, date : {}", userLockDto.getUserId(), userLockDto.getLockReason(), userLockDto.getLockDate());
        return "정지 완료";
    }
}
