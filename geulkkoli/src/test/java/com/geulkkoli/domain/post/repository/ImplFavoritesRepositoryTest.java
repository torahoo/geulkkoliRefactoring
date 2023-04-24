package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.Favorites;
import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ImplFavoritesRepositoryTest {
    /**
     * 단위 테스트 구현을 위한 구현체
     */
    @Autowired
    private ImplPostRepository implPostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImplFavoritesRepository implFavoritesRepository;

    private User user;
    private Post post01, post02;

    @AfterEach
    void afterEach () {
        implFavoritesRepository.clear();
    }

    @BeforeEach
    void init(){
        User save = User.builder()
                .email("test@naver.com")
                .userName("test")
                .nickName("test")
                .phoneNo("00000000000")
                .password("123")
                .gender("male").build();

        user = userRepository.save(save);
    }

    @BeforeEach
    void beforeEach () {
        post01 = implPostRepository.save(Post.builder()
                .user(user)
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build()
        );
        post02 = implPostRepository.save(Post.builder()
                .user(user)
                .nickName("test")
                .postBody("testBody")//채&훈
                .title("testTitle").build()
        );
    }

    @Test
    public void 좋아요_저장 () {
        //given
        Favorites favorites = Favorites.builder()
                .post(post01)
                .user(user)
                .build();

        //when
        Favorites save = implFavoritesRepository.save(favorites);

        //then
        Assertions.assertThat(favorites.getPost()).isEqualTo(save.getPost());
        Assertions.assertThat(favorites.getPost().getTitle()).isEqualTo("여러분");
        Assertions.assertThat(favorites.getUser().getEmail()).isEqualTo("test@naver.com");
    }

    @Test
    public void 중복_좋아요_확인 () throws Exception {
        //given
        Favorites favorites = Favorites.builder()
                .post(post01)
                .user(user)
                .build();

        //when
        Favorites save = implFavoritesRepository.save(favorites);
        Favorites save2 = implFavoritesRepository.save(favorites);

        //then
        Assertions.assertThat(save.getFavoritesId()).isEqualTo(save2.getFavoritesId());
    }

    @Test
    public void 좋아요_하나_조회 (){
        //given
        Favorites favorites = Favorites.builder()
                .post(post01)
                .user(user)
                .build();
        Favorites save = implFavoritesRepository.save(favorites);

        //when
        Favorites find = implFavoritesRepository.findById(save.getFavoritesId())
                .orElseThrow(() -> new NoSuchElementException("can't find favoritesId:" + save.getFavoritesId()));

        //then
        Assertions.assertThat(find).isEqualTo(save);
        Assertions.assertThat(find.getUser().getEmail()).isEqualTo("test@naver.com");
        Assertions.assertThat(find.getPost().getTitle()).isEqualTo("여러분");
    }

    @Test
    public void 전체_좋아요_조회 () throws Exception {
        //given
        Favorites favorites = Favorites.builder()
                .post(post01)
                .user(user)
                .build();
        Favorites save = implFavoritesRepository.save(favorites);

        //when
        List<Favorites> all = implFavoritesRepository.findAll();

        //then
        Assertions.assertThat(all.size()).isEqualTo(1);
        Assertions.assertThat(all.get(0).getPost().getTitle()).isEqualTo("여러분");
        Assertions.assertThat(all.get(0).getUser().getEmail()).isEqualTo("test@naver.com");
    }

    @Test
    public void 좋아요_삭제 () throws Exception {
        //given
        Favorites favorites = Favorites.builder()
                .post(post01)
                .user(user)
                .build();
        Favorites save = implFavoritesRepository.save(favorites);

        Favorites favorites2 = Favorites.builder()
                .post(post02)
                .user(user)
                .build();
        Favorites save2 = implFavoritesRepository.save(favorites2);

        //when
        implFavoritesRepository.delete(save.getFavoritesId());
        List<Favorites> all = implFavoritesRepository.findAll();

        //then
        Assertions.assertThat(all.size()).isEqualTo(1);
        Assertions.assertThat(all.get(0).getPost().getTitle()).isEqualTo("testTitle");
        Assertions.assertThat(all.get(0).getUser().getEmail()).isEqualTo("test@naver.com");
    }
}