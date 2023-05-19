package com.geulkkoli.application.user;

import com.geulkkoli.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserModelDto {

    private Long userId;

    private String userName;

    private String password;

    private String nickName;

    private String email;

    private String phoneNo;

    private String gender;

    private String signUpDate;

    @Builder
    public UserModelDto(Long userId, String userName, String password, String nickName, String email, String phoneNo, String gender, String signUpDate) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.signUpDate = signUpDate;
    }


    public static UserModelDto toDto(User user) {
        return UserModelDto.builder()
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNo(user.getPhoneNo())
                .userName(user.getUserName())
                .gender(user.getGender())
                .signUpDate(user.getSignUpDate())
                .build();
    }
}
