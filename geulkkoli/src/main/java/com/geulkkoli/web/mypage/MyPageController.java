package com.geulkkoli.web.mypage;

import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.application.user.service.PasswordService;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.social.SocialInfoService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.myPage.dto.MyPageFormDto;
import com.geulkkoli.web.myPage.dto.calendar.CalendarDto;
import com.geulkkoli.web.myPage.dto.edit.PasswordEditFormDto;
import com.geulkkoli.web.myPage.dto.edit.UserInfoEditFormDto;
import com.geulkkoli.web.mypage.ConnectedSocialInfos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final PostService postService;
    private final PasswordService passwordService;
    private final SocialInfoService socialInfoService;

    @GetMapping()
    public String myPage(@ModelAttribute("myPageForm") MyPageFormDto myPageFormDto, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser, Model model) {
        myPageFormDto.myPageFormDto(authUser.getUserRealName(), authUser.getUsername());
        model.addAttribute("myPageForm", myPageFormDto);

        ConnectedSocialInfos connectedInfos = socialInfoService.findConnectedInfos(authUser.getUsername());
        model.addAttribute("connectedInfos", connectedInfos);

        return MY_PAGE_FORM;
    }

    @GetMapping("/editUserInfo")
    public String editForm(@AuthenticationPrincipal CustomAuthenticationPrinciple authUser, Model model) {
        log.info("authUser : {}", authUser.getNickName());
        UserInfoEditFormDto userInfoEditFormDto = UserInfoEditFormDto.form(authUser.getUserRealName(), authUser.getNickName(), authUser.getPhoneNo(), authUser.getGender());
        model.addAttribute("userInfoEditForm", userInfoEditFormDto);
        return EDIT_USER_INFO_FORM;
    }

    /*
     * authUser가 기존의 세션 저장 방식을 대체한다
     * */
    //회원 정보 수정
    @PostMapping("/editUserInfo")
    public String editForm(@Validated @ModelAttribute("userInfoEditForm") UserInfoEditFormDto userInfoEditFormDto, BindingResult bindingResult, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        // 닉네임 중복 검사 && 본인의 기존 닉네임과 일치해도 중복이라고 안 뜨게
        if (userService.isNickNameDuplicate(userInfoEditFormDto.getNickName()) && !userInfoEditFormDto.getNickName().equals(authUser.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
        }

        if (userService.isPhoneNoDuplicate(userInfoEditFormDto.getPhoneNo()) && !userInfoEditFormDto.getPhoneNo().equals(authUser.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
        }

        if (bindingResult.hasErrors()) {
            return EDIT_USER_INFO_FORM;
        } else {
            userService.edit(parseLong(authUser), userInfoEditFormDto);
            // 세션에 저장된 authUser의 정보를 수정한다.
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomAuthenticationPrinciple newAuth = (CustomAuthenticationPrinciple) principal;
            log.info("nickName : {}", userInfoEditFormDto.getNickName());
            newAuth.modifyNickName(userInfoEditFormDto.getNickName());
            newAuth.modifyPhoneNo(userInfoEditFormDto.getPhoneNo());
            newAuth.modifyGender(userInfoEditFormDto.getGender());
            newAuth.modifyUserRealName(userInfoEditFormDto.getUserName());
        }
        return REDIRECT_EDIT_USER_INFO;
    }

    @GetMapping("/editPassword")
    public String editPasswordForm(@ModelAttribute("passwordEditForm") PasswordEditFormDto form) {
        return EDIT_PASSWORD_FORM;
    }

    @PostMapping("/editPassword")
    public String editPassword(@Validated @ModelAttribute("passwordEditForm") PasswordEditFormDto form, BindingResult bindingResult, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser, RedirectAttributes redirectAttributes) {
        User user = userService.findById(parseLong(authUser));
        //null값 처리
        if (!passwordService.isPasswordVerification(user, form)) {
            bindingResult.rejectValue("oldPassword", "Check.password");
        }

        if (!form.getNewPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Check.verifyPassword");
        }

        if (bindingResult.hasErrors()) {
            return EDIT_PASSWORD_FORM;
        } else {
            passwordService.updatePassword(parseLong(authUser), form.getNewPassword());
            redirectAttributes.addAttribute("status", true);
        }

        return REDIRECT_EDIT_USER_INFO;
    }

    //활동량에 따른 달력 잔디 심기
    @GetMapping("/calendar")
    @ResponseBody
    public ResponseEntity<CalendarDto> calendaring(@AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
        User user = userService.findById(parseLong(authUser));

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
            User findUser = userService.findById(userId);
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
