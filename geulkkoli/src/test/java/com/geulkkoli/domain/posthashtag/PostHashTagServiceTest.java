package com.geulkkoli.domain.posthashtag;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.hashtag.HashTagRepository;
import com.geulkkoli.domain.hashtag.HashTagType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Post post, post01, post02, post03;
    private HashTag tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9;

    List<Post> posts;

    private String searchTitle = "제목";
    private String searchPostBody = "본문";
    private String searchNickName = "닉네임";
    private String searchHashTag = "해시태그";
    private String searchTitleWords = "test";
    private String searchBodyWords = "test";
    private String searchNickNameWords = "test";
    private String searchHashTagWords = "일반글";

    @BeforeEach
    void init() {
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
    void beforeEach() {
        for (int i = 0; i < 10; i++) {
            AddDTO addDTO = AddDTO.builder()
                    .title("testTitle" + i)
                    .postBody("test postbody " + i)
                    .nickName(user.getNickName())
                    .build();
            post = user.writePost(addDTO);
            postRepository.save(post);
        }


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

        AddDTO addDTO03 = AddDTO.builder()
                .title("testTitle03")
                .postBody("test postbody 03")
                .nickName(user.getNickName())
                .build();
        post03 = user.writePost(addDTO03);
        postRepository.save(post03);

        posts = postRepository.findAll();

        tag1 = hashTagRepository.save(new HashTag("일반글", HashTagType.GENERAL));
        tag2 = hashTagRepository.save(new HashTag("공지글", HashTagType.MANAGEMENT));
        tag3 = hashTagRepository.save(new HashTag("판타지", HashTagType.GENERAL));
        tag4 = hashTagRepository.save(new HashTag("코미디", HashTagType.GENERAL));
        tag5 = hashTagRepository.save(new HashTag("단편소설", HashTagType.GENERAL));
        tag6 = hashTagRepository.save(new HashTag("시", HashTagType.GENERAL));
        tag7 = hashTagRepository.save(new HashTag("이상", HashTagType.GENERAL));
        tag8 = hashTagRepository.save(new HashTag("게임", HashTagType.GENERAL));
        tag9 = hashTagRepository.save(new HashTag("판게아", HashTagType.GENERAL));
    }

    @Test
    public void 게시물에_해시태그_추가_및_ID로_찾기() throws Exception {
        //given
        Long postHashTagId = postHashTagService.addHashTagToPost(post01, tag1);

        //when
        PostHashTag find = postHashTagService.findByPostHashTagId(postHashTagId);

        //then
        assertThat(postHashTagId).isEqualTo(find.getPostHashTagId());
        assertThat(tag1).isEqualTo(find.getHashTag());
    }

//    @Test
//        public void 해시태그로_내용_검색어_찾기() throws Exception {
//        //given
//        Pageable pageable = PageRequest.of(5,5);
//
//        Long save1 = postHashTagService.addHashTagToPost(post01, tag1);
//        Long save2 = postHashTagService.addHashTagToPost(post02, tag1);
//        //when
//        List<ListDTO> listDTOS = postHashTagService.searchPostsListByHashTag(pageable, searchPostBody, searchBodyWords).toList();
//
//        //then
//        assertThat(listDTOS.size()).isEqualTo(2);
//        assertThat(listDTOS.get(0).getPostId()).isEqualTo(post01.getPostId());
//        assertThat(listDTOS.get(1).getPostId()).isEqualTo(post02.getPostId());
//    }
//
//    @Test
//        public void 해시태그로_해시태그_검색어_찾기() throws Exception {
//        //given
//        Pageable pageable = PageRequest.of(5,5);
//
//        Long save1 = postHashTagService.addHashTagToPost(post01, tag1);
//        Long save2 = postHashTagService.addHashTagToPost(post02, tag2);
//
//        //when
//        List<ListDTO> listDTOS = postHashTagService.searchPostsListByHashTag(pageable, searchHashTag, searchHashTagWords).toList();
//        Post post = postRepository.findById(listDTOS.get(0).getPostId()).get();
//        List<PostHashTag> postHashTags = new ArrayList<>(post.getPostHashTags());
//
//        //then
//        assertThat(listDTOS.size()).isEqualTo(1);
//        assertThat(postHashTags.get(0).getHashTag()).isEqualTo(tag1);
//    }

    @Test
    @DisplayName("검색어 분리 기능 테스트")
    public void searchWordExtractorTest() {
        //given
        String searchWords = "우리 함께 즐겨요 #판타지 #코미디";

        //when
        List<HashTag> hashTags = postHashTagService.hashTagSeparator(searchWords);
        String searchWord = postHashTagService.searchWordExtractor(searchWords);

        //then
        assertThat(hashTags).contains(tag3, tag4);
        assertThat(searchWord).isEqualTo("우리 함께 즐겨요");

    }

    @Test
    @DisplayName("태그에 따른 게시글을 잘 가져오는지 테스트")
    public void searchPostContainAllHashTagsTest() {
        //given


        List<HashTag> hashTags = new ArrayList<>(Set.of(tag1, tag2));
        List<HashTag> hashTags2 = new ArrayList<>(Set.of(tag4, tag5));


        List<List<HashTag>> hashTagLists = new ArrayList<>();
        hashTagLists.add(new ArrayList<>(Set.of(tag1, tag3)));
        hashTagLists.add(new ArrayList<>(Set.of(tag2, tag8, tag5)));
        hashTagLists.add(new ArrayList<>(Set.of(tag1, tag2, tag4)));
        hashTagLists.add(new ArrayList<>(Set.of(tag1, tag2, tag7, tag8)));
        hashTagLists.add(new ArrayList<>(Set.of(tag1, tag3, tag6)));
        hashTagLists.add(new ArrayList<>(Set.of(tag1, tag2, tag6, tag7, tag3)));
        hashTagLists.add(new ArrayList<>(Set.of(tag1, tag2, tag8, tag5, tag4)));
        hashTagLists.add(new ArrayList<>(Set.of(tag2, tag6, tag7)));
        hashTagLists.add(new ArrayList<>(Set.of(tag8, tag2, tag3, tag7)));
        hashTagLists.add(new ArrayList<>(Set.of(tag1, tag5, tag8)));

        for (int i = 0; i < 10; i++) {
            postHashTagService.addHashTagsToPost(posts.get(i), hashTagLists.get(i));
        }

        //when
        List<Post> posts = postHashTagService.searchPostContainAllHashTags(hashTags);
        List<Post> posts2 = postHashTagService.searchPostContainAllHashTags(hashTags2);


        //then
        assertThat(posts.size()).isEqualTo(4);
        assertThat(posts2.size()).isEqualTo(1);

    }


    @Test
    @DisplayName("실제로 검색 타입, 검색어에 따라 잘 찾을 수 있는지")
    public void searchPostsListByHashTagVer2() {
        //given
        postHashTagService.addHashTagsToPost(post01, new ArrayList<>(Set.of(tag1, tag3)));

        Pageable pageable = PageRequest.of(5, 5);
        String searchWords = "01 #일반글";
        String searchType = "제목";

        //when
        Page<ListDTO> listDTOS = postHashTagService.searchPostsListByHashTag(pageable, searchType, searchWords);
        List<ListDTO> collect = listDTOS.get().collect(Collectors.toList());

        //then
        assertThat(collect.size()).isEqualTo(1);

    }

    @Test
    @DisplayName("해시태그를 검색하여 게시물이 나오는지")
    public void 해시태그를타입으로검색하기() throws Exception {
        //given
        postHashTagService.addHashTagsToPost(post01,new ArrayList<>(Set.of(tag1, tag3)));
        postHashTagService.addHashTagsToPost(post02,new ArrayList<>(Set.of(tag1, tag3, tag4)));
        postHashTagService.addHashTagsToPost(post,new ArrayList<>(Set.of(tag1, tag9)));
        postHashTagService.addHashTagsToPost(post03,new ArrayList<>(Set.of(tag2, tag3, tag9)));

        Pageable pageable = PageRequest.of(5,5);
        String searchWords = "판 #일반글";
        String searchType = "해시태그";
        String searchWords2 = "판 #공지글";

        //when
        Page<ListDTO> listDTOS = postHashTagService.searchPostsListByHashTag(pageable, searchType, searchWords);
        List<ListDTO> collect = listDTOS.get().collect(Collectors.toList());
        Page<ListDTO> listDTOS2 = postHashTagService.searchPostsListByHashTag(pageable, searchType, searchWords2);
        List<ListDTO> collect2 = listDTOS2.get().collect(Collectors.toList());
        log.info("collect02(01)={}", collect2.get(0).getTitle());


        //then
        assertThat(collect.size()).isEqualTo(3);
        assertThat(collect2.size()).isEqualTo(1);
    }

}