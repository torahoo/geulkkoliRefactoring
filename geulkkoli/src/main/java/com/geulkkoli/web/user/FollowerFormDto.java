package com.geulkkoli.web.user;

import com.geulkkoli.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class FollowerFormDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String nickName;

    @Builder
    public FollowerFormDto(Long userId, String userName, String nickName) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
    }

    public static FollowerFormDto toDTO (User user) {
        return FollowerFormDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .build();
    }

}
