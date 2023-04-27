package com.geulkkoli.domain.post;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PostRepositoryTest {

    /**
     * 단위 테스트 구현을 위한 구현체
     */
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
    }

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
        AddDTO addDTO = AddDTO.builder()
                .title("testTitle")
                .postBody("test postbody")
                .nickName("점심뭐먹지").build();

        Post post = user.writePost(addDTO);
        postRepository.save(post);


        AddDTO addDTO1 = AddDTO.builder()
                .title("testTitle01")
                .postBody("test postbody 01")
                .nickName("점심뭐먹지").build();
        Post post1 = user.writePost(addDTO1);

        postRepository.save(post1);

        AddDTO addDTO2 = AddDTO.builder()
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName("점심뭐먹지").build();

        Post post2 = user.writePost(addDTO2);

        postRepository.save(post2);

        AddDTO addDTO3 = AddDTO.builder()
                .title("testTitle03")
                .postBody("test postbody 03")
                .nickName("점심뭐먹지").build();

        postRepository.save(user.writePost(addDTO3));
    }

    @Test
    void save() {
        Post post = user.writePost(AddDTO.builder()
                .title("testTitle")
                .postBody("test postbody")
                .nickName("점심뭐먹지").build());
        Post save = postRepository.save(post);
        assertThat(save.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    void findById() {
        Post post = user.writePost(AddDTO.builder()
                .title("testTitle")
                .postBody("test postbody")
                .nickName("점심뭐먹지").build());

        Post save = postRepository.save(post);

        Post find = postRepository.findById(save.getPostId())
                .orElseThrow(() -> new NoSuchElementException("No post found id matches : " + save.getPostId()));

        assertThat(save).isEqualTo(find);
    }

    @Test
    void findAll() {
        List<Post> all = postRepository.findAll();
        assertThat(4).isEqualTo(all.size());
    }



    @Test
    void update() {
        Post post = user.writePost(AddDTO.builder()
                .title("testTitle")
                .postBody("test postbody")
                .nickName("점심뭐먹지").build());
        Post savePost = postRepository.save(post);

        Post modifyPost = user.editPost(savePost.getPostId(),new EditDTO(savePost.getPostId(),"modifyTitle","modifyBody",savePost.getNickName()));

        postRepository.save(modifyPost);

        Post one = postRepository.findById(savePost.getPostId())
                .orElseThrow(() -> new NoSuchElementException("No post found id matches : " + savePost.getPostId()));
        assertThat(one.getTitle()).isEqualTo(modifyPost.getTitle());
        assertThat(one.getPostBody()).isEqualTo(modifyPost.getPostBody());
    }

    @Test
    void delete() {
        Post post = user.writePost(AddDTO.builder()
                .title("testTitle")
                .postBody("test postbody")
                .nickName("점심뭐먹지").build());
        Post savePost = postRepository.save(post);
        Post deletedPost = user.deletePost(savePost.getPostId());
        postRepository.delete(deletedPost);
        List<Post> all = postRepository.findAll();

        assertThat(all.size()).isEqualTo(4);
    }

}