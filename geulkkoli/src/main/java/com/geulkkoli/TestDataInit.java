package com.geulkkoli;

import com.geulkkoli.domain.admin.Report;
import com.geulkkoli.domain.admin.ReportRepository;
import com.geulkkoli.domain.admin.service.AdminServiceImpl;
import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.hashtag.HashTagRepository;
import com.geulkkoli.domain.hashtag.HashTagType;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserFindService;
import com.geulkkoli.domain.user.service.UserService;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.user.dto.JoinFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("dev")
public class TestDataInit {

    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final AdminServiceImpl adminServiceImpl;
    private final UserService userService;
    private final PostService postService;
    private final HashTagRepository hashTagRepository;
    private final UserFindService userFindService;

    /**
     * 확인용 초기 데이터 추가
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");

        /*
         * 신고받은 게시물 더미 데이터*/

        /*
         * 시큐리티가 제공하는 비밀번호 암호화를 userService에서 쓰기 때문에
         * userService로 테스트 데이터를 저정한다.
         * */

        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setEmail("tako99@naver.com");
        joinForm.setUserName("김");
        joinForm.setNickName("바나나11");
        joinForm.setPhoneNo("9190232333");
        joinForm.setGender("male");
        joinForm.setPassword("qwe123!!!");
        User user = userService.signUp(joinForm);


//        JoinFormDto joinForm1 = new JoinFormDto();
//        joinForm1.setEmail("kimpjh1@naver.com");
//        joinForm1.setUserName("김");
//        joinForm1.setNickName("바나나121");
//        joinForm1.setPhoneNo("9290232333");
//        joinForm1.setGender("male");
//        joinForm1.setPassword("123");
//        userService.signUp(joinForm1);
        adminServiceImpl.lockUser(user.getUserId(), "비밀번호가 너무 길어요", 7L);

        JoinFormDto joinForm2 = new JoinFormDto();
        joinForm2.setEmail("test01@naver.com");
        joinForm2.setUserName("테스트유저");
        joinForm2.setNickName("준호떡볶이도둑");
        joinForm2.setPhoneNo("01012345678");
        joinForm2.setGender("male");
        joinForm2.setPassword("123");
        User user2 = userService.signUp(joinForm2);

        // 이메일 테스트하느라 실제 이메일 사용 중
        JoinFormDto joinForm3 = new JoinFormDto();
        joinForm3.setEmail("plaz7@naver.com");
        joinForm3.setUserName("신채안");
        joinForm3.setNickName("이메일테스트해봄");
        joinForm3.setPhoneNo("01089188913");
        joinForm3.setGender("female");
        joinForm3.setPassword("123");
        userService.signUp(joinForm3);

        joinForm.setEmail("admin");
        joinForm.setUserName("타코다치");
        joinForm.setNickName("우무문어");
        joinForm.setPhoneNo("01033132232");
        joinForm.setGender("male");
        joinForm.setPassword("123");
        userService.signUpAdmin(joinForm);

        User user01 = userFindService.findById(1L);
        User user02 = userFindService.findById(2L);


        HashTag hashTagCategory1 = HashTag.builder()
                .hashTagName("시")
                .hashTagType(HashTagType.CATEGORY)
                .build();
        HashTag hashTagCategory2 = HashTag.builder()
                .hashTagName("소설")
                .hashTagType(HashTagType.CATEGORY)
                .build();

        HashTag hashTagCategory3 = HashTag.builder()
                .hashTagName("수필")
                .hashTagType(HashTagType.CATEGORY)
                .build();

        HashTag hashTagStatus1 = HashTag.builder()
                .hashTagName("단편")
                .hashTagType(HashTagType.STATUS)
                .build();
        HashTag hashTagStatus2 = HashTag.builder()
                .hashTagName("연재")
                .hashTagType(HashTagType.STATUS)
                .build();
        HashTag hashTagStatus3 = HashTag.builder()
                .hashTagName("완결")
                .hashTagType(HashTagType.STATUS)
                .build();

        HashTag hashTagManagement1 = HashTag.builder()
                .hashTagName("공지글")
                .hashTagType(HashTagType.MANAGEMENT)
                .build();


        hashTagRepository.save(hashTagCategory1);
        hashTagRepository.save(hashTagCategory2);
        hashTagRepository.save(hashTagCategory3);
        hashTagRepository.save(hashTagStatus1);
        hashTagRepository.save(hashTagStatus2);
        hashTagRepository.save(hashTagStatus3);
        hashTagRepository.save(hashTagManagement1);

        JoinFormDto joinForm4 = new JoinFormDto();
        joinForm4.setEmail("calendar@naver.com");
        joinForm4.setUserName("김캘린");
        joinForm4.setNickName("캘린더");
        joinForm4.setPhoneNo("01098765432");
        joinForm4.setGender("female");
        joinForm4.setPassword("123");
        LocalDate signUpDate = LocalDate.of(2022, 1, 1);
        User user4 = userService.signUp(joinForm4, signUpDate);

        AddDTO addDTONotice = AddDTO.builder()
                .title("공지사항입니다")
                .postBody("나는 멋지고 섹시한 개발자")
                .nickName(user01.getNickName())
                .tagListString("#testTag1 #공지글")
                .build();
        adminServiceImpl.saveNotice(addDTONotice, user01);

        for (int i = 0; i < 16; ++i) {

            AddDTO addDTO = AddDTO.builder()
                    .title("여러분")
                    .postBody("나는 멋지고 섹시한 개발자")
                    .nickName(user01.getNickName())
                    .tagListString("#testTag1 #일반글")
                    .tagCategory("#시")
                    .tagStatus("#단편")
                    .build();
            postService.savePost(addDTO, user01);

            AddDTO addDTO1 = AddDTO.builder()
                    .title("testTitle01")
                    .postBody("test postbody 01")
                    .nickName(user01.getNickName())
                    .tagListString("#testTag1 #일반글")
                    .tagCategory("#소설")
                    .tagStatus("#완결")
                    .build();
            postService.savePost(addDTO1, user01);

            AddDTO addDTO2 = AddDTO.builder()
                    .title("testTitle02")
                    .postBody("test postbody 02")
                    .nickName(user01.getNickName())
                    .tagListString("#testTag2 #일반글")
                    .tagCategory("#시")
                    .tagStatus("#연재")
                    .nickName(user2.getNickName())
                    .build();
            postService.savePost(addDTO2, user01);
            Post post2 = user2.writePost(addDTO2);
            postRepository.save(post2);

            AddDTO addDTO3 = AddDTO.builder()
                    .title("testTitle03")
                    .postBody("test postbody 03")
                    .nickName(user02.getNickName())
                    .tagListString("#testTag2 #일반글")
                    .tagCategory("#수필")
                    .tagStatus("#연재")
                    .build();
            postService.savePost(addDTO3, user02);
        }

        for (int i = 7; i <= 12; i++) {
            AddDTO addDTO = AddDTO.builder()
                    .title("달력 테스트")
                    .postBody("햄버거")
                    .nickName(user4.getNickName())
                    .build();
            LocalDateTime createAt = LocalDateTime.of(2022, i, i, 1, 1);
            Post post4 = user4.writePost(addDTO, createAt);
            postRepository.save(post4);
        }

        for (int i = 1; i <= 6; i++) {
            AddDTO addDTO = AddDTO.builder()
                    .title("달력 테스트")
                    .postBody("햄버거")
                    .nickName(user4.getNickName())
                    .build();
            LocalDateTime createAt = LocalDateTime.of(2023, i, i, 1, 1);
            Post post4 = user4.writePost(addDTO, createAt);
            postRepository.save(post4);
        }


        /**
         * 신고받은 게시물 더미 데이터를 리팩토링한 방식으로 다시 작성해봤습니다.
         */
        Report report = user.writeReport(postRepository.findById(2L).get(), "욕설");
        Report report1 = user.writeReport(postRepository.findById(1L).get(), "비 협조적");
        Report report2 = user.writeReport(postRepository.findById(4L).get(), "점심을 안먹음");
        Report report3 = user2.writeReport(postRepository.findById(4L).get(), "욕먹음");
        reportRepository.save(report);
        reportRepository.save(report1);
        reportRepository.save(report2);
        reportRepository.save(report3);
    }

}
