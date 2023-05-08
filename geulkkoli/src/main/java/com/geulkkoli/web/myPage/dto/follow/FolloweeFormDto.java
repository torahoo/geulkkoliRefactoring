package com.geulkkoli.web.myPage.dto.follow;

import com.geulkkoli.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class FolloweeFormDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String nickName;

    @Builder
    public FolloweeFormDto(Long userId, String userName, String nickName) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
    }

    public static FolloweeFormDto toDto (User user) {
        return FolloweeFormDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .build();
    }


}
