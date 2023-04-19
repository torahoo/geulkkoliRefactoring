package com.geulkkoli.domain.user;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_No", nullable = false, unique = true)
    private String phoneNo;

    private String gender;

    @Builder
    public User(String userName, String password, String nickName, String email, String phoneNo, String gender) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }

//    public boolean matchPassword(String password) {
//        return password.matches(this.password);
//    }

    public void updateUser(String userName, String nickName, String phoneNo, String gender){
        this.userName = userName;
        this.nickName = nickName;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }
    public void updatePassword(String password) {
        this.password = password;
    }


}
