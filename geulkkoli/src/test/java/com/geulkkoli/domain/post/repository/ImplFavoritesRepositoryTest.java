package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.favorites.ImplFavoritesRepository;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.favorites.Favorites;
import com.geulkkoli.domain.post.Post;
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

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ImplFavoritesRepositoryTest {
    /**
     * 단위 테스트 구현을 위한 구현체
     */
    @Autowired
    private PostRepository postRepository;
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
        post01 = postRepository.save(Post.builder()
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build()
        );
        post02 = postRepository.save(Post.builder()
                .nickName("test")
                .postBody("testBody")//채&훈
                .title("testTitle").build()
        );

        post01.addAuthor(user);
        post02.addAuthor(user);
    }

    @Test
    public void 좋아요_저장 () {
        //given
        Favorites favorite = new Favorites();
        favorite.addAuthor(user);
        favorite.addPost(post01);

        //when
        Favorites save = implFavoritesRepository.save(favorite);

        //then
        Assertions.assertThat(favorite.getPost()).isEqualTo(save.getPost());
        Assertions.assertThat(favorite.getPost().getTitle()).isEqualTo("여러분");
        Assertions.assertThat(favorite.getUser().getEmail()).isEqualTo("test@naver.com");
    }

    /**
     * 동일 내용이 persist될 시에 같은 id 값을 가지는지 테스트
     */
    @Test
    public void 중복_좋아요_확인 () throws Exception {
        //given
        Favorites favorite = new Favorites();
        favorite.addAuthor(user);
        favorite.addPost(post01);

        //when
        Favorites save = implFavoritesRepository.save(favorite);
        Favorites save2 = implFavoritesRepository.save(favorite);

        //then
        Assertions.assertThat(save.getFavoritesId()).isEqualTo(save2.getFavoritesId());
    }

    @Test
    public void 좋아요_하나_조회 (){
        //given
        Favorites favorite = new Favorites();
        favorite.addAuthor(user);
        favorite.addPost(post01);
        Favorites save = implFavoritesRepository.save(favorite);

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
        Favorites favorite = new Favorites();
        favorite.addAuthor(user);
        favorite.addPost(post01);
        Favorites save = implFavoritesRepository.save(favorite);

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
        Favorites favorite = new Favorites();
        favorite.addAuthor(user);
        favorite.addPost(post01);
        Favorites save = implFavoritesRepository.save(favorite);

        Favorites favorite2 = new Favorites();
        favorite2.addAuthor(user);
        favorite2.addPost(post02);
        Favorites save2 = implFavoritesRepository.save(favorite2);

        //when
        implFavoritesRepository.delete(save.getFavoritesId());
        List<Favorites> all = implFavoritesRepository.findAll();

        //then
        Assertions.assertThat(all.size()).isEqualTo(1);
        Assertions.assertThat(all.get(0).getPost().getTitle()).isEqualTo("testTitle");
        Assertions.assertThat(all.get(0).getUser().getEmail()).isEqualTo("test@naver.com");
    }
}