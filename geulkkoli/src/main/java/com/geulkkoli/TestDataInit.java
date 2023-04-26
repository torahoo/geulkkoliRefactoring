
package com.geulkkoli;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.domain.admin.ReportRepository;
import com.geulkkoli.domain.admin.service.AdminServiceImpl;
import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.user.dto.JoinFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("dev")
public class TestDataInit {

    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final UserSecurityService userSecurityService;
    private final AdminServiceImpl adminServiceImpl;
    private final UserService userService;

    /**
     * 확인용 초기 데이터 추가
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");

        /*
         * 신고받은 게시물 더미 데이터*/

        /*
         * 시큐리티가 제공하는 비밀번호 암호화를 userService에서 쓰기 때문에
         * userService로 테스트 데이터를 저정한다.
         * */

        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setEmail("tako99@naver.com");
        joinForm.setUserName("김");
        joinForm.setNickName("바나나11");
        joinForm.setPhoneNo("9190232333");
        joinForm.setGender("male");
        joinForm.setPassword("qwe123!!!");
        User user = userSecurityService.join(joinForm);
        adminServiceImpl.rockUser(user.getUserId(), "비밀번호가 너무 길어요");

        JoinFormDto joinForm2 = new JoinFormDto();
        joinForm2.setEmail("test01@naver.com");
        joinForm2.setUserName("테스트유저");
        joinForm2.setNickName("준호떡볶이도둑");
        joinForm2.setPhoneNo("01012345678");
        joinForm2.setGender("male");
        joinForm2.setPassword("123");
        userSecurityService.join(joinForm2);
        User user02 = userService.findById(2L);

        joinForm.setEmail("admin");
        joinForm.setUserName("타코다치");
        joinForm.setNickName("우무문어");
        joinForm.setPhoneNo("01033132232");
        joinForm.setGender("male");
        joinForm.setPassword("123");
        userSecurityService.joinAdmin(joinForm);

        reportRepository.save(Report.of(postRepository.findById(2L).get(), user, LocalDateTime.now(), "욕설"));
        reportRepository.save(Report.of(postRepository.findById(1L).get(), user, LocalDateTime.now(), "비 협조적"));
        reportRepository.save(Report.of(postRepository.findById(4L).get(), user, LocalDateTime.now(), "점심을 안먹음"));

        User user01 = userService.findById(1L);

        AddDTO addDTO = AddDTO.builder()
                .title("여러분")
                .postBody("나는 멋지고 섹시한 개발자")
                .nickName(user01.getNickName())
                .build();
        Post post = user01.writePost(addDTO);
        postRepository.save(post);


        AddDTO addDTO1 = AddDTO.builder()
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName(user01.getNickName())
                .build();
        Post post1 = user01.writePost(addDTO1);
        postRepository.save(post1);


        AddDTO addDTO2 = AddDTO.builder()
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName(user01.getNickName())
                .build();
        Post post2 = user01.writePost(addDTO2);
        postRepository.save(post2);

        AddDTO addDTO3 = AddDTO.builder()
                .title("testTitle03")
                .postBody("test postbody 03")
                .nickName(user01.getNickName())
                .build();
        Post post3 = user01.writePost(addDTO3);
        postRepository.save(post3);
    }

}
