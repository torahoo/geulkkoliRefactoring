package com.geulkkoli.web.mypage;

import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.user.dto.mypage.calendar.CalendarDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    public static final String MY_PAGE_FORM = "mypage/mypage";
    public static final String EDIT_USER_INFO_FORM = "mypage/edit/userInfoEditForm";
    public static final String EDIT_PASSWORD_FORM = "mypage/edit/passwordEditForm";
    public static final String REDIRECT_EDIT_USER_INFO = "redirect:/myPage/editUserInfo";
    public static final String REDIRECT_INDEX = "redirect:/";

    private final UserService userService;
    private final UserFindService userFindService;
    private final PostService postService;


    //활동량에 따른 달력 잔디 심기
    @GetMapping("/calendar")
    @ResponseBody
    public ResponseEntity<CalendarDto> calendaring(@AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        User user = userFindService.findById(parseLong(authUser));

        List<LocalDate> allPostDatesByOneUser = postService.getCreatedAts(user);
        CalendarDto calendarDto = new CalendarDto(authUser.getUserRealName(), user.getSignUpDate(), allPostDatesByOneUser);

        return ResponseEntity.ok(calendarDto);
    }

    /**
     * 서비스에서 쓰는 객체의 이름은 User인데 memberDelete라는 이름으로 되어 있어서 통일성을 위해 이름을 고친다.
     * 또한 사용자 입장에서는 자신의 정보를 삭제하는 게 아니라 탈퇴하는 서비스를 쓰고 있으므로 uri를 의미에 더 가깝게 고쳤다.
     */
    @DeleteMapping("unsubscribe/{userId}")
    public String unsubscribe(@PathParam("userId") Long userId, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        try {
            User findUser = userFindService.findById(userId);
            userService.delete(findUser);
        } catch (Exception e) {
            //만약 findUser가 null이라면? 다른 에러페이지를 보여줘야하지 않을까?
            return REDIRECT_INDEX;
        }
        return REDIRECT_INDEX;
    }

    private Long parseLong(CustomAuthenticationPrinciple authUser) {
        return Long.valueOf(authUser.getUserId());
    }
}
