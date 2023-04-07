
package com.geulkkoli;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
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
                .nickName("testTitle01")
                .postBody("test postbody 01")//채&훈
                .title("test nickname01").build()
        );
        postRepository.save(Post.builder()
                .nickName("testTitle02")
                .postBody("test postbody 02")//채&훈
                .title("test nickname02").build()
        )
        ;postRepository.save(Post.builder()
                .nickName("testTitle03")
                .postBody("test postbody 03")//채&훈
                .title("test nickname03").build()
        );

        userRepository.save(User.builder()
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .phoneNo("01012345678")
                .gender("male")
                .build());
    }

}
