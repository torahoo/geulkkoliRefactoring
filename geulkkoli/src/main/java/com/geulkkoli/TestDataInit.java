package com.geulkkoli;

import com.geulkkoli.domain.Post;
import com.geulkkoli.domain.User;
import com.geulkkoli.infrastructure.PostRepository;
import com.geulkkoli.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
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
                .title("여러분 ㅋ").build()
        );

        userRepository.save(User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build());
    }

}
