package com.geulkkoli;

import com.geulkkoli.domain.Post;
import com.geulkkoli.domain.User;
import com.geulkkoli.respository.PostRepository;
import com.geulkkoli.respository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final PostRepository postRepository;
    private final UsersRepository userRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        postRepository.save(Post.builder()
                .nickName("나")
                .postBody("나나")
                .title("테스트").build()
        );

        userRepository.save(User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build());
    }

}
