package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.HashTags;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ImplHashTagsRepositoryTest {

    /**
     * 단위 테스트 구현을 위한 구현체
     */
    @Autowired
    private ImplPostRepository implPostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImplHashTagsRepository implHashTagsRepository;

    private User user;
    private Post post01, post02;

    @AfterEach
    void afterEach () {
        implHashTagsRepository.clear();
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
                .nickName("바나나")
                .postBody("나는 멋지고 섹시한 개발자")//채&훈
                .title("여러분").build()
        );
        post02 = implPostRepository.save(Post.builder()
                .nickName("test")
                .postBody("testBody")//채&훈
                .title("testTitle").build()
        );
        user.writePost(post01);
        user.writePost(post02);
    }

    @Test
    public void 해시태그_저장 () {
        //given
        HashTags hashTag = HashTags.builder()
                .hashTagName("장르01")
                .build();
        post01.addHashTag(hashTag);

        //when
        HashTags save = implHashTagsRepository.save(hashTag);

        //then
        assertThat(save.getHashTagName()).isEqualTo("장르01");
        assertThat(save.getPost()).isEqualTo(post01);
    }

    @Test
    public void 게시글_해시태그_저장 () throws Exception {
        //given
        HashTags hashTag = HashTags.builder()
                .hashTagName("장르01")
                .build();
        post01.addHashTag(hashTag);

        //when
        HashTags save = implHashTagsRepository.save(hashTag);
        ArrayList<HashTags> hashtags = new ArrayList<>(post01.getHashTags());

        //then
        assertThat(hashtags.get(0).getHashTagName()).isEqualTo("장르01");
        assertThat(hashtags.size()).isEqualTo(1);
        assertThat(hashtags.contains(save)).isTrue();
        assertThat(hashtags.get(0).getHashTagsId()).isEqualTo(save.getHashTagsId());
    }

    @Test
    public void 좋아요_하나_조회 () {
        //given
        HashTags hashTag = HashTags.builder()
                .hashTagName("장르01")
                .build();
        post01.addHashTag(hashTag);

        HashTags save = implHashTagsRepository.save(hashTag);
        //when
        HashTags find = implHashTagsRepository.findById(save.getHashTagsId())
                .orElseThrow(() -> new NoSuchElementException("there's no such ID:" + save.getHashTagsId()));

        //then
        assertThat(find).isEqualTo(save);
        assertThat(find.getHashTagsId()).isEqualTo(save.getHashTagsId());
        assertThat(find.getHashTagName()).isEqualTo("장르01");
    }

    @Test
    public void 전체_조회 () {
        //given
        HashTags hashTag = HashTags.builder()
                .hashTagName("장르01")
                .build();
        post01.addHashTag(hashTag);

        HashTags save = implHashTagsRepository.save(hashTag);
        //when
        List<HashTags> all = implHashTagsRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getHashTagName()).isEqualTo("장르01");
    }

    @Test
    public void 해시태그_삭제 () {
        //given
        HashTags hashTag = HashTags.builder()
                .hashTagName("장르01")
                .build();
        post01.addHashTag(hashTag);

        HashTags save = implHashTagsRepository.save(hashTag);

        HashTags hashTag2 = HashTags.builder()
                .hashTagName("장르02")
                .build();
        post01.addHashTag(hashTag2);

        HashTags save2 = implHashTagsRepository.save(hashTag2);

        //when
        implHashTagsRepository.delete(save.getHashTagsId());
        List<HashTags> all = implHashTagsRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getHashTagName()).isEqualTo("장르02");
        assertThat(all.get(0).getHashTagsId()).isEqualTo(save2.getHashTagsId());
    }
}