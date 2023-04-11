package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.EditService;
import com.geulkkoli.domain.user.service.JoinService;
import com.geulkkoli.domain.user.service.LoginService;
import com.geulkkoli.web.user.edit.EditForm;
import com.geulkkoli.web.user.edit.EditPasswordForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    public static final String LOGIN_FORM = "user/loginForm";
    public static final String JOIN_FORM = "user/joinForm";
    public static final String EDIT_FORM = "user/edit/editForm";
    public static final String EDIT_PASSWORD_FORM = "user/edit/editPassword";
    public static final String REDIRECT_INDEX = "redirect:/";
    private final LoginService loginService;
    private final JoinService joinService;
    private final EditService editService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return LOGIN_FORM;
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        log.info("email {} , password {}", form.getEmail(), form.getPassword());
        if (bindingResult.hasErrors()) {
            return LOGIN_FORM;
        }

        Optional<User> loginUser = loginService.login(form.getEmail(), form.getPassword());

        if (loginUser.isEmpty()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return LOGIN_FORM;
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser.get());
        return REDIRECT_INDEX;
    }

    //join
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinForm form) {
        return JOIN_FORM;
    }

    @PostMapping("/join")
    public String userJoin(@Validated @ModelAttribute("joinForm") JoinForm form, BindingResult bindingResult, Model model) {
        log.info("join Method={}", this);

        if (joinService.isEmailDuplicate(form.getEmail())) {
            bindingResult.rejectValue("email", "Duple.joinForm.email");
            return JOIN_FORM;
        }

        if (joinService.isNickNameDuplicate(form.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
            return JOIN_FORM;

        }

        if (joinService.isPhoneNoDuplicate(form.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
            return JOIN_FORM;

        }

        if (!form.getPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Duple.joinForm.verifyPassword");
            return JOIN_FORM;

        }

        if (!bindingResult.hasErrors()) {
            joinService.join(form.toEntity());
        }

        log.info("model = {}", model);
        log.info("form = {}", form);

        return REDIRECT_INDEX;
    }


    @GetMapping("/edit")
    public String editForm(@ModelAttribute("editForm") EditForm editForm, HttpServletRequest httpServletRequest, Model model) {
       HttpSession session = httpServletRequest.getSession(false);
        User user= (User) session.getAttribute(SessionConst.LOGIN_USER);
        editForm.editForm(user.getUserName(), user.getNickName(), user.getPhoneNo(), user.getGender());
        model.addAttribute("editForm", editForm);
        return EDIT_FORM;
    }

    @PostMapping("/edit")
    public String editForm(@Validated @ModelAttribute("editForm") EditForm editForm, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        if (editService.isNickNameDuplicate(editForm.getNickName()) && !editForm.getNickName().equals(user.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
            return EDIT_FORM;
        }

        if (editService.isPhoneNoDuplicate(editForm.getPhoneNo()) && !editForm.getPhoneNo().equals(user.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
            return EDIT_FORM;
        }

        if (!bindingResult.hasErrors()) {
            editService.update(user.getUserId(), editForm, httpServletRequest);
            log.info("form = {}", editForm);
        }

        return "redirect:/edit";
    }

    @GetMapping("/editPassword")
    public String editPasswordForm(@ModelAttribute("editPasswordForm") EditPasswordForm form) {
        return EDIT_PASSWORD_FORM;
    }

    @PostMapping("/editPassword")
    public String editPassword(@Valid @ModelAttribute("editPasswordForm") EditPasswordForm form, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        if (!editService.isPasswordVerification(user, form)) {
            bindingResult.rejectValue("password", "Duple.joinForm.password");
            return EDIT_PASSWORD_FORM;
        }

        if (!form.getPassword().equals(form.getVerifyNewPassword())) {
            bindingResult.rejectValue("verifyNewPassword", "Duple.joinForm.verifyPassword");
            return EDIT_PASSWORD_FORM;
        }

        if (!bindingResult.hasErrors()) {
            editService.updatePassword(user, form);
        }

        return "redirect:/" + EDIT_FORM;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return REDIRECT_INDEX;
    }
}
