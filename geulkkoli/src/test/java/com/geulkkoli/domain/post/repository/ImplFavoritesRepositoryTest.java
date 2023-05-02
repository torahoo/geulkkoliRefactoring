package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.favorites.ImplFavoritesRepository;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.favorites.Favorites;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

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
        AddDTO addDTO01 = AddDTO.builder()
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName(user.getNickName())
                .build();
        Post post01 = user.writePost(addDTO01);

        AddDTO addDTO02 = AddDTO.builder()
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName(user.getNickName())
                .build();
        Post post02 = user.writePost(addDTO02);
    }

    /**
     * comment를 유저쪽에 옮겨 테스트 하기위해 일단 주석 처리
     */

//
//    @Test
//    public void 좋아요_저장 () {
//        //given
//        Favorites favorite = new Favorites();
//
//        //when
//        Favorites save = implFavoritesRepository.save(favorite);
//        post01.addFavorite(save);
//        user.pressFavorite(save);
//
//        //then
//        assertThat(favorite.getPost()).isEqualTo(save.getPost());
//        assertThat(favorite.getPost().getTitle()).isEqualTo("여러분");
//        assertThat(favorite.getUser().getEmail()).isEqualTo("test@naver.com");
//    }
//
//    /**
//     * 유저가 해당 좋아요 정보를 가지고 있는지 체크
//     */
//    @Test
//    public void 유저_좋아요_저장 () throws Exception {
//        //given
//        Favorites save = implFavoritesRepository.save(new Favorites());
//        post01.addFavorite(save);
//        user.pressFavorite(save);
//
//        //when
//        ArrayList<Favorites> favorites = new ArrayList<>(user.getFavorites());
//
//        //then
//        assertThat(favorites.size()).isEqualTo(1);
//        assertThat(favorites.contains(save)).isEqualTo(true);
//        assertThat(favorites.get(0).getPost().getPostBody()).isEqualTo("나는 멋지고 섹시한 개발자");
//    }
//
//    /**
//     * 게시물에 해당 좋아요 정보를 가지고 있는지 체크
//     */
//    @Test
//    public void 게시물_좋아요_저장 () throws Exception {
//        //given
//        Favorites save = implFavoritesRepository.save(new Favorites());
//        post01.addFavorite(save);
//        user.pressFavorite(save);
//
//        //when
//        ArrayList<Favorites> favorites = new ArrayList<>(post01.getFavorites());
//
//        //then
//        assertThat(favorites.size()).isEqualTo(1);
//        assertThat(favorites.contains(save)).isEqualTo(true);
//        assertThat(favorites.get(0).getUser().getNickName()).isEqualTo("test");
//    }
//
//    /**
//     * 동일 내용이 persist될 시에 같은 id 값을 가지는지 테스트
//     */
//    @Test
//    public void 중복_좋아요_확인 () throws Exception {
//        //given
//        Favorites favorite = new Favorites();
//
//        //when
//        Favorites save = implFavoritesRepository.save(favorite);
//        post01.addFavorite(save);
//        user.pressFavorite(save);
//
//        Favorites save2 = implFavoritesRepository.save(favorite);
//        post01.addFavorite(save2);
//        user.pressFavorite(save2);
//
//        //then
//        assertThat(save.getFavoritesId()).isEqualTo(save2.getFavoritesId());
//    }
//
//    @Test
//    public void 좋아요_하나_조회 (){
//        //given
//        Favorites save = implFavoritesRepository.save(new Favorites());
//        post01.addFavorite(save);
//        user.pressFavorite(save);
//
//        //when
//        Favorites find = implFavoritesRepository.findById(save.getFavoritesId())
//                .orElseThrow(() -> new NoSuchElementException("can't find favoritesId:" + save.getFavoritesId()));
//
//        //then
//        assertThat(find).isEqualTo(save);
//        assertThat(find.getUser().getEmail()).isEqualTo("test@naver.com");
//        assertThat(find.getPost().getTitle()).isEqualTo("여러분");
//    }
//
//    @Test
//    public void 전체_좋아요_조회 () throws Exception {
//        //given
//        Favorites save = implFavoritesRepository.save(new Favorites());
//        post01.addFavorite(save);
//        user.pressFavorite(save);
//
//        Favorites save2 = implFavoritesRepository.save(new Favorites());
//        post02.addFavorite(save2);
//        user.pressFavorite(save2);
//
//        //when
//        List<Favorites> all = implFavoritesRepository.findAll();
//
//        //then
//        assertThat(all.size()).isEqualTo(2);
//        assertThat(all.get(0).getPost().getTitle()).isEqualTo("여러분");
//        assertThat(all.get(0).getUser().getEmail()).isEqualTo("test@naver.com");
//    }
//
//    @Test
//    public void 좋아요_삭제 () throws Exception {
//        //given
//        Favorites save = implFavoritesRepository.save(new Favorites());
//        post01.addFavorite(save);
//        user.pressFavorite(save);
//
//        Favorites save2 = implFavoritesRepository.save(new Favorites());
//        post02.addFavorite(save2);
//        user.pressFavorite(save2);
//
//        //when
//        implFavoritesRepository.delete(save.getFavoritesId());
//        List<Favorites> all = implFavoritesRepository.findAll();
//
//        //then
//        assertThat(all.size()).isEqualTo(1);
//        assertThat(all.get(0).getPost().getTitle()).isEqualTo("testTitle");
//        assertThat(all.get(0).getUser().getEmail()).isEqualTo("test@naver.com");
//    }
}