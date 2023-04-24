package com.geulkkoli.web.user;

import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.user.edit.UserInfoEditDto;
import com.geulkkoli.web.user.edit.PasswordEditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    public static final String LOGIN_FORM = "user/loginForm";
    public static final String JOIN_FORM = "user/joinForm";
    public static final String EDIT_FORM = "user/edit/editForm";
    public static final String EDIT_PASSWORD_FORM = "user/edit/editPassword";
    public static final String REDIRECT_INDEX = "redirect:/";
    private final UserService userService;
    private final UserSecurityService userSecurityService;

    @RequestMapping("/loginPage")
    public String loginForm(@ModelAttribute("loginForm") LoginFormDto form) {
        return LOGIN_FORM;
    }

    //join
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinFormDto form) {
        return JOIN_FORM;
    }

    @PostMapping("/join")
    public String userJoin(@Validated @ModelAttribute("joinForm") JoinFormDto form, BindingResult bindingResult, Model model) {
        log.info("join Method={}", this);

        if (userService.isEmailDuplicate(form.getEmail())) {
            bindingResult.rejectValue("email", "Duple.joinForm.email");
        }

        if (userService.isNickNameDuplicate(form.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
        }

        if (userService.isPhoneNoDuplicate(form.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
        }

        // 중복 검사라기보다는 비밀번호 확인에 가까운 것 같아서 에러코드명 변경
        if (!form.getPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Check.verifyPassword");
        }

        if (!bindingResult.hasErrors()) {
            userSecurityService.join(form);

            log.info("joinModel = {}", model);
            log.info("joinForm = {}", form);

            return REDIRECT_INDEX;
        } else {
            return JOIN_FORM;
        }
    }

    @GetMapping("user/edit")
    public String editForm(@ModelAttribute("editForm") UserInfoEditDto userInfoEditDto, @AuthenticationPrincipal AuthUser authUser, Model model) {
        log.info("editForm : {}", userInfoEditDto.toString());
        log.info("authUser : {}",authUser.toString());
        userInfoEditDto.editFormDto(authUser.getUserRealName(), authUser.getNickName(), authUser.getPhoneNo(), authUser.getGender());
        model.addAttribute("editForm", userInfoEditDto);
        return EDIT_FORM;
    }

    /*
     * authUser가 기존의 세션 저장 방식을 대체한다
     * */
    @PostMapping("user/edit")
    public String editForm(@Validated @ModelAttribute("editForm") UserInfoEditDto userInfoEditDto, BindingResult bindingResult, @AuthenticationPrincipal AuthUser authUser) {
        log.info("editForm : {}", userInfoEditDto.toString());
        // 닉네임 중복 검사 && 본인의 기존 닉네임과 일치해도 중복이라고 안 뜨게
        if (userService.isNickNameDuplicate(userInfoEditDto.getNickName()) && !userInfoEditDto.getNickName().equals(authUser.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
        }

        if (userService.isPhoneNoDuplicate(userInfoEditDto.getPhoneNo()) && !userInfoEditDto.getPhoneNo().equals(authUser.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
        }

        if (bindingResult.hasErrors()) {
            return EDIT_FORM;
        } else {
            userService.edit(authUser.getUserId(), userInfoEditDto);
            // 세션에 저장된 authUser의 정보를 수정한다.
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AuthUser newAuth = (AuthUser) principal;
            newAuth.modifyNickName(userInfoEditDto.getNickName());
            newAuth.modifyPhoneNo(userInfoEditDto.getPhoneNo());
            newAuth.modifyGender(userInfoEditDto.getGender());
            newAuth.modifyUserRealName(userInfoEditDto.getUserName());
        }
        return "redirect:/user/edit";
    }

    @GetMapping("user/edit/editPassword")
    public String editPasswordForm(@ModelAttribute("editPasswordForm") PasswordEditDto form) {
        return EDIT_PASSWORD_FORM;
    }

    @PostMapping("user/edit/editPassword")
    public String editPassword(@Validated @ModelAttribute("editPasswordForm") PasswordEditDto form, BindingResult bindingResult, @AuthenticationPrincipal AuthUser authUser, RedirectAttributes redirectAttributes) {
        User user = userService.findById(authUser.getUserId());
        if (!userSecurityService.isPasswordVerification(user, form)) {
            bindingResult.rejectValue("password", "Check.password");
        }

        if (!form.getNewPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Check.verifyPassword");
        }

        if (bindingResult.hasErrors()) {
            return EDIT_PASSWORD_FORM;
        } else {
            userSecurityService.updatePassword(authUser.getUserId(), form);
            redirectAttributes.addAttribute("status", true);
            log.info("editPasswordForm = {}", form);
        }

        return "redirect:/user/edit";
    }

    /**
     * 서비스에서 쓰는 객체의 이름은 User인데 memberDelete라는 이름으로 되어 있어서 통일성을 위해 이름을 고친다.
     * 또한 사용자 입장에서는 자신의 정보를 삭제하는 게 아니라 탈퇴하는 서비스를 쓰고 있으므로 uri를 의미에 더 가깝게 고쳤다.
     */
    @PostMapping("user/edit/unsubscribe")
    public String unsubscribe(@AuthenticationPrincipal AuthUser authUser) {
        try {
            User findUser = userService.findById(authUser.getUserId());
            userService.delete(findUser);
        } catch (Exception e) {
            //만약 findUser가 null이라면? 다른 에러페이지를 보여줘야하지 않을까?
            return "redirect:/";
        }
        return "redirect:/";
    }

}
