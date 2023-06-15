package com.geulkkoli.web.user;

import com.geulkkoli.application.follow.FollowInfo;
import com.geulkkoli.application.follow.FollowInfos;
import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.application.user.EmailService;
import com.geulkkoli.application.user.PasswordService;
import com.geulkkoli.domain.follow.service.FollowFindService;
import com.geulkkoli.domain.social.SocialInfoFindService;
import com.geulkkoli.domain.social.SocialInfoService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.mypage.dto.ConnectedSocialInfos;
import com.geulkkoli.web.mypage.dto.FollowsCount;
import com.geulkkoli.web.user.dto.EmailCheckForJoinDto;
import com.geulkkoli.web.user.dto.JoinFormDto;
import com.geulkkoli.web.user.dto.LoginFormDto;
import com.geulkkoli.web.user.dto.edit.PasswordEditDto;
import com.geulkkoli.web.user.dto.edit.UserInfoEditDto;
import com.geulkkoli.web.user.dto.find.FindEmailFormDto;
import com.geulkkoli.web.user.dto.find.FindPasswordFormDto;
import com.geulkkoli.web.user.dto.find.FoundEmailFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    public static final String FIND_EMAIL_FORM = "user/find/findEmailForm";
    public static final String FOUND_EMAIL_FORM = "user/find/foundEmailForm";
    public static final String FIND_PASSWORD_FORM = "user/find/findPasswordForm";
    public static final String TEMP_PASSWORD_FORM = "user/find/tempPasswordForm";
    public static final String JOIN_FORM = "user/joinForm";
    public static final String EDIT_FORM = "user/edit/editForm";
    public static final String EDIT_PASSWORD_FORM = "user/edit/editPasswordForm";
    public static final String REDIRECT_INDEX = "redirect:/";
    public static final String REDIRECT_EDIT_INDEX = "redirect:/user/edit";
    private final UserService userService;
    private final UserFindService userFindService;
    private final PasswordService passwordService;
    private final SocialInfoService socialInfoService;
    private final FollowFindService followFindService;
    private final SocialInfoFindService socialInfoFindService;
    private final EmailService emailService;


    @GetMapping("/{nickName}")
    public ModelAndView getMyPage(@PathVariable("nickName") String nickName) {
        ConnectedSocialInfos connectedInfos = socialInfoFindService.findAllByNickName(nickName);
        User user = userFindService.findByNickName(nickName);
        log.info("user: {}", user.getUserId());
        Integer followee = followFindService.countFolloweeByFollowerId(user.getUserId());
        Integer follower = followFindService.countFollowerByFolloweeId(user.getUserId());
        FollowsCount followsCount = FollowsCount.of(followee, follower);
        ModelAndView modelAndView = new ModelAndView("mypage/mypage", "connectedInfos", connectedInfos);
        modelAndView.addObject("followsCount", followsCount);
        return modelAndView;
    }

    @GetMapping("/{nickName}/followees")
    public ModelAndView getFollowees(@PathVariable("nickName")String nickName,@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        User user = userFindService.findByNickName(nickName);
        Integer followee = followFindService.countFolloweeByFollowerId(user.getUserId());
        FollowInfos followeeUserInfos = followFindService.findSomeFolloweeByFollowerId(user.getUserId(), null, pageable);
        ModelAndView modelAndView = new ModelAndView("mypage/followdetail", "followers", followeeUserInfos);
        modelAndView.addObject("allCount", followee);

        return modelAndView;
    }

    @GetMapping("/{nickName}/followers")
    public ModelAndView getFollowers(@PathVariable("nickName")String nickName,@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        User user = userFindService.findByNickName(nickName);
        Integer follower = followFindService.countFollowerByFolloweeId(user.getUserId());
        List<FollowInfo> followerUserInfos = followFindService.findSomeFollowerByFolloweeId(user.getUserId(), null, pageable);
        FollowInfos followInfos = FollowInfos.of(followerUserInfos);
        List<Long> userIdByFollowedEachOther = followFindService.findUserIdByFollowedEachOther(followInfos.userIds(), user.getUserId(), pageable.getPageSize());
        followInfos.checkSubscribe(userIdByFollowedEachOther);

        log.info("followInfos: {}", followInfos.getFollowInfos());

        ModelAndView modelAndView = new ModelAndView("mypage/followerdetail", "followers", followInfos);
        modelAndView.addObject("allCount", follower);

        return modelAndView;
    }

    @GetMapping("/findEmail")
    public String findEmailForm(@ModelAttribute("findEmailForm") FindEmailFormDto form) {
        return FIND_EMAIL_FORM;
    }

    @PostMapping("/findEmail")
    public String userFindEmail(@Validated @ModelAttribute("findEmailForm") FindEmailFormDto form, BindingResult bindingResult, Model model) {

        Optional<User> user = userFindService.findByUserNameAndPhoneNo(form.getUserName(), form.getPhoneNo());

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

        Optional<User> user = userFindService.findByEmailAndUserNameAndPhoneNo(form.getEmail(), form.getUserName(), form.getPhoneNo());

        if (user.isEmpty()) {
            bindingResult.addError(new ObjectError("empty", "Check.findContent"));
        }

        if (!bindingResult.hasErrors()) {
            request.getSession().setAttribute("email", user.get().getEmail());
            return "forward:/tempPassword"; // post로 감
        } else {
            return FIND_PASSWORD_FORM;
        }
    }

    @PostMapping("/tempPassword")
    public String tempPasswordForm() {
        return TEMP_PASSWORD_FORM;
    }

    @GetMapping("/tempPassword")
    public String userTempPassword(HttpServletRequest request, Model model) {
        String email = (String) request.getSession().getAttribute("email");
        Optional<User> user = userFindService.findByEmail(email);

        int length = passwordService.setLength(8, 20);
        String tempPassword = passwordService.createTempPassword(length);

        passwordService.updatePassword(user.get().getUserId(), tempPassword);
        emailService.sendTempPasswordEmail(email, tempPassword);
        log.info("email 발송");

        model.addAttribute("waitMailMessage", true);
        return TEMP_PASSWORD_FORM;
    }

    //join
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinFormDto form) {
        return JOIN_FORM;
    }

    @PostMapping("/join")
    public String userJoin(@Validated @ModelAttribute("joinForm") JoinFormDto form, BindingResult bindingResult, HttpServletRequest request) {
        log.info("join Method={}", this);

        if (userService.isNickNameDuplicate(form.getNickName())) {
            bindingResult.rejectValue("nickName", "Duple.nickName");
        }

        if (userService.isPhoneNoDuplicate(form.getPhoneNo())) {
            bindingResult.rejectValue("phoneNo", "Duple.phoneNo");
        }

        String authenticationEmail = (String) request.getSession().getAttribute("authenticationEmail");
        String authenticationNumber = (String) request.getSession().getAttribute("authenticationNumber");
        if (authenticationEmail.isEmpty() || authenticationNumber.isEmpty()) {
            bindingResult.rejectValue("email", "Authentication.email");
        }

        // 인증된 이메일 수정 후 인증 안 된 상태로 가입 시도할 경우
        if (!form.getEmail().equals(authenticationEmail)) {
            bindingResult.rejectValue("email", "Authentication.email");
        }

        if (!form.getPassword().equals(form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Check.verifyPassword");
        }

        if (!bindingResult.hasErrors()) {
            userService.signUp(form);

            return REDIRECT_INDEX;
        } else {
            return JOIN_FORM;
        }
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public ResponseMessage checkEmail(@RequestBody EmailCheckForJoinDto form, HttpServletRequest request) {


        if (form.getEmail().isEmpty()) {
            return ResponseMessage.NULL_OR_BLANK_EMAIL;
        }
        if (userService.isEmailDuplicate(form.getEmail())) {
            return ResponseMessage.EMAIL_DUPLICATION;
        }
        int length = 6;
        String authenticationNumber = passwordService.authenticationNumber(length);
        request.getSession().setAttribute("authenticationNumber", authenticationNumber);
        emailService.sendAuthenticationNumberEmail(form.getEmail(), authenticationNumber);
        log.info("email 발송");

        return ResponseMessage.SEND_AUTHENTICATION_NUMBER_SUCCESS;
    }

    @PostMapping("/checkAuthenticationNumber")
    @ResponseBody
    public String checkAuthenticationNumber(@RequestBody EmailCheckForJoinDto form, HttpServletRequest request) {

        String authenticationNumber = (String) request.getSession().getAttribute("authenticationNumber");
        String responseMessage;

        if (!form.getAuthenticationNumber().trim().equals(authenticationNumber)) {
            responseMessage = "wrong";
        } else {
            request.getSession().setAttribute("authenticationEmail", form.getEmail());
            responseMessage = "right";
        }

        return responseMessage;
    }

    @GetMapping("/edit")
    public ModelAndView editUserInfo(@AuthenticationPrincipal CustomAuthenticationPrinciple authUser, Model model) {
        log.info("authUser : {}", authUser.getNickName());
        ConnectedSocialInfos connectedInfos = socialInfoFindService.findConnectedInfos(authUser.getUsername());
        UserInfoEditDto userInfoEditDto = UserInfoEditDto.from(authUser.getUserRealName(), authUser.getNickName(), authUser.getPhoneNo(), authUser.getGender());
        ModelAndView modelAndView = new ModelAndView().addObject("editForm", userInfoEditDto);
        modelAndView.addObject("connectedInfos", connectedInfos);

        return modelAndView;
    }


    @PostMapping("/edit")
    public String editUserInfo(@Validated @ModelAttribute("editForm") UserInfoEditDto userInfoEditDto, BindingResult bindingResult, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser) {
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
            userService.edit(parseLong(authUser), userInfoEditDto);
            // 세션에 저장된 authUser의 정보를 수정한다.
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomAuthenticationPrinciple newAuth = (CustomAuthenticationPrinciple) principal;
            log.info("nickName : {}", userInfoEditDto.getNickName());
            newAuth.modifyNickName(userInfoEditDto.getNickName());
            newAuth.modifyPhoneNo(userInfoEditDto.getPhoneNo());
            newAuth.modifyGender(userInfoEditDto.getGender());
            newAuth.modifyUserRealName(userInfoEditDto.getUserName());
        }
        return REDIRECT_EDIT_INDEX;
    }

    @GetMapping("/edit/editPassword")
    public String editPasswordForm(@ModelAttribute("editPasswordForm") PasswordEditDto form) {
        return EDIT_PASSWORD_FORM;
    }

    @PostMapping("/edit/editPassword")
    public String editPassword(@Validated @ModelAttribute("editPasswordForm") PasswordEditDto form, BindingResult bindingResult, @AuthenticationPrincipal CustomAuthenticationPrinciple authUser, RedirectAttributes redirectAttributes) {
        User user = userFindService.findById(parseLong(authUser));
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
            log.info("editPasswordForm = {}", form);
        }

        return REDIRECT_EDIT_INDEX;
    }

    /**
     * 서비스에서 쓰는 객체의 이름은 User인데 memberDelete라는 이름으로 되어 있어서 통일성을 위해 이름을 고친다.
     * 또한 사용자 입장에서는 자신의 정보를 삭제하는 게 아니라 탈퇴하는 서비스를 쓰고 있으므로 uri를 의미에 더 가깝게 고쳤다.
     */
    @DeleteMapping("/unsubscribe/{nickName}")
    public String unsubscribe(@PathVariable("nickName") String nickName) {
        try {
            User findUser = userFindService.findByNickName(nickName);
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
