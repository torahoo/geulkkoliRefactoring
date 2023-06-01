package com.geulkkoli.domain.hashtag;

import com.geulkkoli.domain.favorites.Favorites;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void afterEach () {
        hashTagRepository.deleteAll();
    }

    @Test
        public void 해시태그_시퀀스번호_확인() throws Exception {
        //given
        HashTag hashTag = new HashTag("일반글");
        HashTag save = hashTagRepository.save(hashTag);
        HashTag hashTag2 = new HashTag("공지글");
        HashTag save2 = hashTagRepository.save(hashTag2);
        //when
        long checkSeq = save.getHashTagId()+1;
        //then
        log.info("hashTagId1={}",save.getHashTagId());
        log.info("hashTagId2={}",save2.getHashTagId());
        Assertions.assertThat(checkSeq).isEqualTo(save2.getHashTagId());
    }
}