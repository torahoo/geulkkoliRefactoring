package com.geulkkoli.domain.hashtag;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class HashTagRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashTagRepository hashTagRepository;



    private User user;
    private Post post01, post02;

    @AfterEach
    void afterEach() {
        hashTagRepository.deleteAll();
    }

    @Test
    void 해시태그_시퀀스번호_확인() throws Exception {
        //given
        HashTag hashTag = new HashTag("일반글");
        HashTag save = hashTagRepository.save(hashTag);
        HashTag hashTag2 = new HashTag("공지글");
        HashTag save2 = hashTagRepository.save(hashTag2);
        //when
        long checkSeq = save.getHashTagId() + 1;
        //then
        log.info("hashTagId1={}", save.getHashTagId());
        log.info("hashTagId2={}", save2.getHashTagId());
        Assertions.assertThat(checkSeq).isEqualTo(save2.getHashTagId());
    }


    @Test
    void findHashTagIds() {
        //given
        HashTag hashTag = new HashTag("일반글");
        HashTag save = hashTagRepository.save(hashTag);
        HashTag hashTag2 = new HashTag("공지글");
        HashTag save2 = hashTagRepository.save(hashTag2);
        List<String> hashTagNames = new ArrayList<>();
        hashTagNames.add("일반글");
        hashTagNames.add("공지글");
        //when
        //then
        List<Long> hashIdsByHashTagNames = hashTagRepository.hashIdsByHashTagNames(hashTagNames);

        assertThat(hashIdsByHashTagNames.get(0)).isEqualTo(save.getHashTagId());
        assertThat(hashIdsByHashTagNames.get(1)).isEqualTo(save2.getHashTagId());
    }

    @Test
    void findByHashTageAndPostTitle() {
//        //given
//        HashTag hashTag = new HashTag("일반글");
//        HashTag saveHashTag = hashTagRepository.save(hashTag);
//        HashTag hashTag2 = new HashTag("공지글");
//        HashTag saveHashTag2 = hashTagRepository.save(hashTag2);
//        User unSaveUser = User.builder()
//                .email("test@gmail.com")
//                .userName("test")
//                .nickName("test")
//                .phoneNo("00000000000")
//                .password("123")
//                .gender("male")
//                .build();
//
//        User unSavedUser2 = User.builder()
//                .email("test2@gmail.com")
//                .userName("test2")
//                .nickName("test2")
//                .phoneNo("00000000001")
//                .password("123")
//                .gender("male")
//                .build();
//
//
//        User saveUser = userRepository.save(unSaveUser);
//        User saveUser2 = userRepository.save(unSavedUser2);
//
//        Post post = Post.builder()
//                .title("일반1")
//                .postBody("일반 내용")
//                .user(saveUser)
//                .hashTag(saveHashTag)
//                .build();
//
//        Post post = Post.builder()
//                .title("일반2")
//                .postBody("일반 내용")
//                .user(saveUser)
//                .(saveHashTag)
//                .build();
//
//        Post post2 = Post.builder()
//                .title("공지1")
//                .content("공지 내용")
//                .user(saveUser2)
//                .hashTag(saveHashTag2)
//                .build();
//
//        Post savePost = postRepository.save(post);
//        Post savePost2 = postRepository.save(post2);
//
//        //when
//
//        hashTagRepository.allPostHashTagsByHashTagName("일반글");
    }
}