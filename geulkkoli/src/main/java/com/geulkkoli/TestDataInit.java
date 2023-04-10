
package com.geulkkoli;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.user.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        postRepository.save(Post.builder()
                .nickName("륜투더환")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build()
        );

        postRepository.save(Post.builder()
                .title("testTitle01")
                .postBody("test postbody 01")//채&훈
                .nickName("test nickname01").build()
        );
        postRepository.save(Post.builder()
                .title("testTitle02")
                .postBody("test postbody 02")//채&훈
                .nickName("test nickname02").build()
        )
        ;postRepository.save(Post.builder()
                .title("testTitle03")
                .postBody("test postbody 03")//채&훈
                .nickName("test nickname03").build()
        );

        JoinForm joinForm = new JoinForm();
        joinForm.setEmail("tako99@naver.com");
        joinForm.setUserName("김");
        joinForm.setNickName("바나나11");
        joinForm.setPhoneNo("9190232333");
        joinForm.setGender("male");
        joinForm.setPassword("12345678");
        userService.join(joinForm);
    }

}
