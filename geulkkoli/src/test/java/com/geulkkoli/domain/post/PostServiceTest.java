package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;
import com.geulkkoli.web.post.dto.ListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        AddDTO addDTO = new AddDTO(1L, "title", "body", "nick");
        postService.savePost(addDTO, user1);

        Post post = postService.findById(1L);

        assertThat("title").isEqualTo(post.getTitle());
    }

    @Test
    @Transactional
    void findAll() {


        User user1 = User.builder()
                .email("email@email.com")
                .userName("userName")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        userRepository.save(user1);

        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);

        String searchType = "";
        String searchWords = "";

        assertThat(postService.searchPostFindAll(PageRequest.of(5, 5), searchType, searchWords).toList().size()).isEqualTo(4);
    }

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


        Post post= postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);
        EditDTO editDTO = new EditDTO(post.getPostId(),"title update", "body update", "nick update");
        postService.updatePost(post.getPostId(), editDTO, user1);

        Post one = postService.findById(1L);


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

        postService.savePost(new AddDTO(1L, "title", "body", "nick"), user1);

        postService.deletePost(1L);

        String searchType = "";
        String searchWords = "";

        List<ListDTO> all = postService.searchPostFindAll(PageRequest.of(5, 5), searchType, searchWords).toList();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    void getCreatedAts(){
        User user = User.builder()
                .email("created@ats.com")
                .userName("우하하")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        LocalDate signUpDate = LocalDate.of(2022, 2, 2);
        user.setCreatedAtForCalendarTest(signUpDate);

        userRepository.save(user);

        AddDTO addDTO1 = AddDTO.builder()
                .title("달력")
                .postBody("요호")
                .nickName(user.getNickName())
                .build();
        LocalDateTime createAt1 = LocalDateTime.of(2022, 3, 7, 1, 1);
        user.writePost(addDTO1, createAt1);

        AddDTO addDTO2 = AddDTO.builder()
                .title("달력")
                .postBody("요호")
                .nickName(user.getNickName())
                .build();
        LocalDateTime createAt2 = LocalDateTime.of(2023, 4, 8, 12, 1);
        user.writePost(addDTO2, createAt2);

        List<LocalDate> createdAts = postService.getCreatedAts(user);

        assertThat(createdAts.size()).isEqualTo(2);
        assertThat(createdAts).contains(createAt1.toLocalDate());
        assertThat(createdAts).contains(createAt2.toLocalDate());

    }

}