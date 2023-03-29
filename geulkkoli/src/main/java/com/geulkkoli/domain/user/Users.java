package com.geulkkoli.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDbId;
    private String userWebId;
    private String userName;

    private String password;
    private String nickName;

    @Builder
    public Users(String userId, String userName, String password, String nickName) {
        this.userWebId = userId;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
    }
}
