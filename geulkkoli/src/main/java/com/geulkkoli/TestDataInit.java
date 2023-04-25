
package com.geulkkoli;

import com.geulkkoli.application.security.UserSecurityService;
import com.geulkkoli.domain.admin.AccountLockRepository;
import com.geulkkoli.domain.admin.service.AdminServiceImpl;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.user.JoinFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("dev")
public class TestDataInit {

    private final PostRepository postRepository;
    private final UserSecurityService userSecurityService;
    private final AdminServiceImpl adminServiceImpl;
    private final AccountLockRepository accountLockRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        postRepository.save(Post.builder()
                .authorId(1L)
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build());

        postRepository.save(Post.builder()

                .authorId(2L)
                .title("testTitle01")
                .postBody("test postbody 01")//채&훈
                .nickName("점심뭐먹지").build()
        );
        postRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle02")
                .postBody("test postbody 02")//채&훈
                .nickName("점심뭐먹지").build()
        )
        ;
        postRepository.save(Post.builder()
                .authorId(2L)
                .title("testTitle03")
                .postBody("test postbody 03")//채&훈
                .nickName("점심뭐먹지").build()
        );

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
        userSecurityService.join(joinForm);

        adminServiceImpl.rockUser(1L,"비밀번호가 너무 길어요");
    }

}
