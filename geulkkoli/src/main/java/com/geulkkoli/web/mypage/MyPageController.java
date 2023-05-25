package com.geulkkoli.web.mypage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/my-page")
public class MyPageController {

    @GetMapping()
    public ModelAndView getMyPage() {
        return new ModelAndView("mypage/mypage");
    }
}
