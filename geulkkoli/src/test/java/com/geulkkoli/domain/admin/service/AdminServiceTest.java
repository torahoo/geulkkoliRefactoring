package com.geulkkoli.domain.admin.service;

import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.admin.ReportRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.topic.Topic;
import com.geulkkoli.domain.topic.TopicRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.admin.DailyTopicDto;
import com.geulkkoli.web.admin.ReportDto;
import com.geulkkoli.web.post.dto.AddDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminServiceImpl;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    private User user;
    private User user2;

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
    }

    @BeforeEach
    void init() {
        for(int i = 0; i<150; i++) {
            topicRepository.save(Topic.builder().topicName("testTopic"+i).build());
        }

        User save = User.builder()
                .email("test@naver.com")
                .userName("test")
                .nickName("test")
                .phoneNo("00000000000")
                .password("123")
                .gender("male").build();

        User save2 = User.builder()
                .email("test2@naver.com")
                .userName("test2")
                .nickName("test2")
                .phoneNo("00000000002")
                .password("123")
                .gender("male").build();

        user = userRepository.save(save);
        user2 = userRepository.save(save2);

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
                .title("testTitle02")
                .postBody("test postbody 02")
                .nickName("점심뭐먹지").build();

        Post post3 = user.writePost(addDTO3);

        postRepository.save(post3);
    }

    @Test
    @DisplayName("게시글 작성자 조회")
    void findUserByPostId() {
        //given
        Long postId = 1L;

        //when
        User userByPostId = adminServiceImpl.findUserByPostId(postId);

        //then
        assertThat(userByPostId).isEqualTo(user);
    }

    @Test
    @DisplayName("신고받은 게시글 조회")
    void findReportedPosts() {
        //given
        Report report = user.writeReport(postRepository.findById(2L).get(), "욕설");
        Report report1 = user.writeReport(postRepository.findById(1L).get(), "비 협조적");
        Report report2 = user.writeReport(postRepository.findById(4L).get(), "점심을 안먹음");
        Report report3 = user2.writeReport(postRepository.findById(4L).get(), "욕먹음");
        reportRepository.save(report);
        reportRepository.save(report1);
        reportRepository.save(report2);
        reportRepository.save(report3);

        //when
        List<ReportDto> reportedPosts = adminServiceImpl.findAllReportedPost();

        //then
        assertThat(reportedPosts.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("유저 잠금")
    void lockUser() {
        //given
        Long userId = user.getUserId();

        //when
        adminServiceImpl.lockUser(userId, "욕설", 3L);

        //then
        user.getAccountLocks().forEach(accountLock -> assertThat(accountLock.getReason()).isEqualTo("욕설"));

    }



    @Test @DisplayName("중간에 주제가 빈 날짜가 있을 때 제대로 채워주는지")
    void fillTopicTest() {
        //given
        List<Topic> topicsByUseDateBefore = topicRepository.findTopicsByUseDateBefore(LocalDate.now());


        for(int i=0; i<30; i++){
            if(i==4||i==8||i==17||i==28){
                continue;
            }
            topicsByUseDateBefore.get(i).settingUpComingDate(LocalDate.now().plusDays(i));
        }


        List<Topic> topics = topicRepository.findTopicByUpComingDateBetween(LocalDate.now(), LocalDate.now().plusDays(29), Sort.by(Sort.Direction.ASC, "upComingDate"));


        //when
        topics = adminServiceImpl.fillTopic(topics);

        //then
        assertThat(topics.size()).isEqualTo(30);
        topics=null;
    }

    @Test @DisplayName("주제를 Dto로 잘 바꿨는지")
    void findWeeklyTopicTest(){
        //given
        List<Topic> topicsByUseDateBefore = topicRepository.findTopicsByUseDateBefore(LocalDate.now());

        for(int i=0; i<30; i++){
            if(i==4||i==8||i==17||i==28){
                continue;
            }
            topicsByUseDateBefore.get(i).settingUpComingDate(LocalDate.now().plusDays(i));
        }

        //when
        List<DailyTopicDto> weeklyTopic = adminServiceImpl.findWeeklyTopic();

        //then
        assertThat(weeklyTopic.size()).isEqualTo(30);
    }
}