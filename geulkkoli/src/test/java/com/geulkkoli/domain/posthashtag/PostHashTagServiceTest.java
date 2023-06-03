package com.geulkkoli.domain.posthashtag;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.hashtag.HashTagRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class PostHashTagServiceTest {

    @Autowired
    private PostHashTagService postHashTagService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashTagRepository hashTagRepository;

    private User user;
    private Post post01, post02;
    private HashTag tag1, tag2, tag3, tag4;

    private String searchTitle = "제목";
    private String searchPostBody = "본문";
    private String searchNickName = "닉네임";
    private String searchHashTag = "해시태그";
    private String searchTitleWords = "test";
    private String searchBodyWords = "test";
    private String searchNickNameWords = "test";
    private String searchHashTagWords = "일반글";

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
        post01 = user.writePost(addDTO01);
        postRepository.save(post01);

        AddDTO addDTO02 = AddDTO.builder()
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName(user.getNickName())
                .build();
        post02 = user.writePost(addDTO02);
        postRepository.save(post02);

        tag1 = hashTagRepository.save(new HashTag("일반글"));
        tag2 = hashTagRepository.save(new HashTag("공지글"));
        tag3 = hashTagRepository.save(new HashTag("판타지"));
        tag4 = hashTagRepository.save(new HashTag("코미디"));
    }

    @Test
        public void 게시물에_해시태그_추가_및_ID로_찾기 () throws Exception {
        //given
        Long postHashTagId = postHashTagService.addHashTagToPost(post01, tag1);

        //when
        PostHashTag find = postHashTagService.findByPostHashTagId(postHashTagId);

        //then
        assertThat(postHashTagId).isEqualTo(find.getPostHashTagId());
        assertThat(tag1).isEqualTo(find.getHashTag());
    }

    @Test
        public void 해시태그로_내용_검색어_찾기() throws Exception {
        //given
        Pageable pageable = PageRequest.of(5,5);

        Long save1 = postHashTagService.addHashTagToPost(post01, tag1);
        Long save2 = postHashTagService.addHashTagToPost(post02, tag1);
        //when
        List<ListDTO> listDTOS = postHashTagService.searchPostsListByHashTag(pageable, searchPostBody, searchBodyWords, tag1).toList();

        //then
        assertThat(listDTOS.size()).isEqualTo(2);
        assertThat(listDTOS.get(0).getPostId()).isEqualTo(post01.getPostId());
        assertThat(listDTOS.get(1).getPostId()).isEqualTo(post02.getPostId());
    }

    @Test
        public void 해시태그로_해시태그_검색어_찾기() throws Exception {
        //given
        Pageable pageable = PageRequest.of(5,5);

        Long save1 = postHashTagService.addHashTagToPost(post01, tag1);
        Long save2 = postHashTagService.addHashTagToPost(post02, tag2);

        //when
        List<ListDTO> listDTOS = postHashTagService.searchPostsListByHashTag(pageable, searchHashTag, searchHashTagWords, tag1).toList();
        Post post = postRepository.findById(listDTOS.get(0).getPostId()).get();
        List<PostHashTag> postHashTags = new ArrayList<>(post.getPostHashTags());

        //then
        assertThat(listDTOS.size()).isEqualTo(1);
        assertThat(postHashTags.get(0).getHashTag()).isEqualTo(tag1);
    }

    @Test
    @DisplayName("검색어 분리 기능 테스트")
    public void tagSeparatorTest(){
        //given
        String searchWords = "우리 함께 즐겨요 #판타지 #코미디";

        //when
        List<HashTag> hashTags = postHashTagService.hashTagSeparator(searchWords);
        String searchWord = postHashTagService.searchWordExtractor(searchWords);

        //then
        assertThat(hashTags).contains(tag3, tag4);
        assertThat(searchWord).isEqualTo("우리 함께 즐겨요");

    }
}