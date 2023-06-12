package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.posthashtag.PostHashTag;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;
import com.geulkkoli.web.post.dto.ListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestDataInit Class 참조
 */
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    void findById() {

        User user1 = User.builder()
                .email("email@email.com")
                .userName("userName")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        userRepository.save(user1);

        AddDTO addDTO = new AddDTO(1L, "title", "body", "nick", "#testTag");
        Post save = postService.savePost(addDTO, user1);

        Post post = postService.findById(save.getPostId());
        List<PostHashTag> postHashTags = new ArrayList<>(post.getPostHashTags());

        assertThat("title").isEqualTo(post.getTitle());
        assertThat("testTag").isEqualTo(postHashTags.get(0).getHashTag().getHashTagName());
    }

    /**
     * 앞으로 안쓸 기능 입니다.
     */
//    @Test
//    @Transactional
//    void findAll() {
//        User user1 = User.builder()
//                .email("email@email.com")
//                .userName("userName")
//                .gender("gender")
//                .password("password")
//                .phoneNo("phoneNo")
//                .nickName("nickName")
//                .build();
//
//        User loginUser = userRepository.save(user1);
//
//        Post post01 = postService.savePost(new AddDTO(loginUser.getUserId(), "title1", "body1", loginUser.getNickName(), "#testTag1"), loginUser);
//        Post post02 = postService.savePost(new AddDTO(loginUser.getUserId(), "title2", "body2", loginUser.getNickName(), "#testTag1"), loginUser);
//        Post post03 = postService.savePost(new AddDTO(loginUser.getUserId(), "title3", "body3", loginUser.getNickName(), "#testTag2"), loginUser);
//        Post post04 = postService.savePost(new AddDTO(loginUser.getUserId(), "title4", "body4", loginUser.getNickName(), "#testTag2"), loginUser);
//
//        String searchType = "";
//        String searchWords = "";
//
//        List<ListDTO> listDTOS = postService.searchPostFindAll(PageRequest.of(10, 10), searchType, searchWords).toList();
//
//        assertThat(listDTOS.size()).isEqualTo(4);
//    }

    @Test
    @Transactional
    void updatePost() {
        User user1 = User.builder()
                .email("email@email.com")
                .userName("userName")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        userRepository.save(user1);


        Post post= postService.savePost(new AddDTO(1L, "title", "body", "nick", "#testTag"), user1);
        EditDTO editDTO = new EditDTO(post.getPostId(),"title update", "body update", "nick update", "#수정test");
        postService.updatePost(post.getPostId(), editDTO);

        Post one = postService.findById(post.getPostId());


        assertThat("title update").isEqualTo(one.getTitle());
    }

    @Test
    void deletePost() {
        User user1 = User.builder()
                .email("email@email.com")
                .userName("userName")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        userRepository.save(user1);

        Post post = postService.savePost(new AddDTO(1L, "title", "body", user1.getNickName(), "#testTag"), user1);

        postService.deletePost(post.getPostId(), user1.getNickName());

        String searchType = "";
        String searchWords = "";

        List<ListDTO> all = postService.searchPostFindAll(PageRequest.of(5, 5), searchType, searchWords).toList();
        assertThat(all.size()).isEqualTo(0);
    }
}