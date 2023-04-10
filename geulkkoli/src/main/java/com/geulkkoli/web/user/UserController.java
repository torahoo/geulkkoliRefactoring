package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.EditService;
import com.geulkkoli.domain.user.service.JoinService;
import com.geulkkoli.domain.user.service.LoginService;
import com.geulkkoli.web.user.edit.EditUpdateForm;
import com.geulkkoli.web.user.edit.EditViewForm;
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
import javax.servlet.http.HttpServletResponse;
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
            bindingResult.rejectValue("nickName", "Duple.joinForm.nickName");
            return JOIN_FORM;

        }

        if (joinService.isPhoneNoDuplicate(form.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.joinForm.phoneNo");
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


    //edit
//    @GetMapping("/edit")
//    public String editViewForm(@ModelAttribute EditViewForm form) {
//        return EDIT_FORM;
//    }
//    @GetMapping("/edit")
//    public String editViewForm(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);
//        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
//
//        model.addAttribute("User", user);
//        return EDIT_FORM;
//    }

    @GetMapping("/edit")
    public String editViewForm(HttpSession session, Model model) {

        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("User", user);

        return EDIT_FORM;
    }

    @PostMapping("/edit")
    public String editUpdateForm(@ModelAttribute EditUpdateForm form, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        editService.update(user, form);

        if (editService.isNickNameDuplicate(form.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.joinForm.nickName");
            return EDIT_FORM;
        }

        if (editService.isPhoneNoDuplicate(form.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.joinForm.phoneNo");
            return EDIT_FORM;
        }

        return "redirect:/edit";
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
