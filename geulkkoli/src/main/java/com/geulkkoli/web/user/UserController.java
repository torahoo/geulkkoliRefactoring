package com.geulkkoli.web.user;

import com.geulkkoli.application.user.AuthUser;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.user.edit.EditFormDto;
import com.geulkkoli.web.user.edit.EditPasswordFormDto;
import com.geulkkoli.web.user.find.FindEmailFormDto;
import com.geulkkoli.web.user.find.FindPasswordFormDto;
import com.geulkkoli.web.user.find.FoundEmailFormDto;
import com.geulkkoli.web.user.find.ResetPasswordFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    public static final String LOGIN_FORM = "user/loginForm";
    public static final String FIND_EMAIL_FORM = "user/find/findEmailForm";
    public static final String FOUND_EMAIL_FORM = "user/find/foundEmailForm";
    public static final String FIND_PASSWORD_FORM = "user/find/findPasswordForm";
    public static final String RESET_PASSWORD_FORM = "user/find/resetPasswordForm";
    public static final String JOIN_FORM = "user/joinForm";
    public static final String EDIT_FORM = "user/edit/editForm";
    public static final String EDIT_PASSWORD_FORM = "user/edit/editPasswordForm";
    public static final String REDIRECT_INDEX = "redirect:/";
    private final UserService userService;

    @RequestMapping("/loginPage")
    public String loginForm(@ModelAttribute("loginForm") LoginFormDto form) {
        return LOGIN_FORM;
    }

    @GetMapping("/findEmail")
    public String findEmailForm(@ModelAttribute("findEmailForm") FindEmailFormDto form) {
        return FIND_EMAIL_FORM;
    }

    @PostMapping("/findEmail")
    public String userFindEmail(@Validated @ModelAttribute("findEmailForm") FindEmailFormDto form, BindingResult bindingResult, Model model) {

        Optional<User> user = userService.findByEmail(form.getUserName(), form.getPhoneNo());

        if (user.isEmpty()) {
            bindingResult.addError(new ObjectError("empty", "Check.findContent"));
        }

        if (!bindingResult.hasErrors()) {
            FoundEmailFormDto foundEmail = new FoundEmailFormDto(user.get().getEmail());
            model.addAttribute("email", foundEmail.getEmail());
            return FOUND_EMAIL_FORM;

        } else {
            return FIND_EMAIL_FORM;
        }
    }

    @GetMapping("/foundEmail")
    public String foundEmailForm(@ModelAttribute("foundEmailForm") FoundEmailFormDto form) {
        return FOUND_EMAIL_FORM;
    }

    @GetMapping("/findPassword")
    public String findPasswordForm(@ModelAttribute("findPasswordForm") FindPasswordFormDto form) {
        return FIND_PASSWORD_FORM;
    }

    @PostMapping("/findPassword")
    public String userFindPassword(@Validated @ModelAttribute("findPasswordForm") FindPasswordFormDto form, BindingResult bindingResult, HttpServletRequest request) {

        Optional<User> user = userService.findByEmail(form.getEmail(), form.getUserName(), form.getPhoneNo());

        if (user.isEmpty()) {
            bindingResult.addError(new ObjectError("empty", "Check.findContent"));
        }

        if (!bindingResult.hasErrors()) {
            request.getSession().setAttribute("userId", user.get().getUserId());
            return "forward:/forwardSpace";
        } else {
            return FIND_PASSWORD_FORM;
        }
    }

    @PostMapping("/forwardSpace") // post로 전송된 데이터 유지 & 비밀번호를 reset할 폼으로 이동하기 위해 폼 값 초기화
    public String resetPasswordForm(Model model) {
        model.addAttribute("resetPasswordForm", new ResetPasswordFormDto("", ""));
        return RESET_PASSWORD_FORM;
    }

    @PostMapping("/resetPassword")
    public String userResetPassword(@Validated @ModelAttribute("resetPasswordForm") ResetPasswordFormDto form, BindingResult bindingResult, HttpServletRequest request) {

        Long userId = (Long) request.getSession().getAttribute("userId");

        if (!form.getPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Check.verifyPassword");
        }

        if (!bindingResult.hasErrors()) {
            userService.updatePassword(userId, form.getPassword());
            return "redirect:/loginPage";

        } else {
            return RESET_PASSWORD_FORM;
        }

    }

    //join
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinFormDto form) {
        return JOIN_FORM;
    }

    @PostMapping("/join")
    public String userJoin(@Validated @ModelAttribute("joinForm") JoinFormDto form, BindingResult
            bindingResult, Model model) {
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

        if (!form.getPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Check.verifyPassword");
        }

        if (!bindingResult.hasErrors()) {
            userService.join(form);

            log.info("joinModel = {}", model);
            log.info("joinForm = {}", form);

            return REDIRECT_INDEX;

        } else {
            return JOIN_FORM;
        }
    }

    @GetMapping("/edit")
    public String editForm(@ModelAttribute("editForm") EditFormDto form, @AuthenticationPrincipal AuthUser
            authUser, Model model) {
        form.editFormDto(authUser.getUserName(), authUser.getNickName(), authUser.getPhoneNo(), authUser.getGender());
        model.addAttribute("editForm", form);
        return EDIT_FORM;
    }

    /*
     * authUser가 기존의 세션 저장 방식을 대체한다
     * */
    @PostMapping("/edit")
    public String userEdit(@Validated @ModelAttribute("editForm") EditFormDto form, BindingResult
            bindingResult, @AuthenticationPrincipal AuthUser authUser) {

        if (userService.isNickNameDuplicate(form.getNickName()) && !form.getNickName().equals(authUser.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
        }

        if (userService.isPhoneNoDuplicate(form.getPhoneNo()) && !form.getPhoneNo().equals(authUser.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
        }

        if (!bindingResult.hasErrors()) {
            userService.update(authUser.getUserId(), form);
            return "redirect:/edit";

        } else {
            return EDIT_FORM;
        }
    }

    @GetMapping("/editPassword")
    public String editPasswordForm(@ModelAttribute("editPasswordForm") EditPasswordFormDto form) {
        return EDIT_PASSWORD_FORM;
    }

    @PostMapping("/editPassword")
    public String userEditPassword(@Validated @ModelAttribute("editPasswordForm") EditPasswordFormDto
                                           form, BindingResult bindingResult, @AuthenticationPrincipal AuthUser authUser, RedirectAttributes
                                           redirectAttributes) {

        if (!userService.isPasswordVerification(authUser.getUserId(), form.getPassword())) {
            bindingResult.rejectValue("password", "Check.password");
        }

        if (!form.getNewPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Check.verifyPassword");
        }

        if (!bindingResult.hasErrors()) {
            userService.updatePassword(authUser.getUserId(), form.getNewPassword());
            redirectAttributes.addAttribute("status", true);
            return "redirect:/edit";

        } else {
            return EDIT_PASSWORD_FORM;
        }
    }

    @PostMapping("/memberDelete")
    public String memberDelete(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            userService.delete((User) session.getAttribute(SessionConst.LOGIN_USER));
            session.invalidate();
        }
        return REDIRECT_INDEX;
    }

}
