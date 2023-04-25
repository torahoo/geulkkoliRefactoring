
package com.geulkkoli;

import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.post.repository.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.domain.user.service.UserService;
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
    private final UserService userService;
    /**
     * 확인용 초기 데이터 추가
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");

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
        userService.join(joinForm);
        User user01 = userService.findById(1L);

        JoinFormDto joinForm2 = new JoinFormDto();
        joinForm2.setEmail("test01@naver.com");
        joinForm2.setUserName("테스트유저");
        joinForm2.setNickName("준호떡볶이도둑");
        joinForm2.setPhoneNo("01012345678");
        joinForm2.setGender("male");
        joinForm2.setPassword("123");
        userService.join(joinForm2);
        User user02 = userService.findById(2L);

        Post save01 = postRepository.save(Post.builder()
                .nickName(user01.getNickName())
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분")
                .build());
        save01.addAuthor(user01);

        Post save02 = postRepository.save(Post.builder()
                .title("testTitle01")
                .postBody("test postbody 01")//채&훈
                .nickName(user01.getNickName())
                .build());
        save02.addAuthor(user01);

        Post save03 = postRepository.save(Post.builder()
                .title("testTitle02")
                .postBody("test postbody 02")//채&훈
                .nickName(user02.getNickName())
                .build());
        save03.addAuthor(user02);

        Post save04 = postRepository.save(Post.builder()
                .title("testTitle03")
                .postBody("test postbody 03")//채&훈
                .nickName(user02.getNickName())
                .build());
        save04.addAuthor(user02);
    }

}
