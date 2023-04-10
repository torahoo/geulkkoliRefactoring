package com.geulkkoli.domain.user;

import com.geulkkoli.application.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name", nullable = false, unique = true)
    private String userName;

    @Getter()
    @Column(name = "password", nullable = false,unique = true)
    private String password;

    @Column(name = "nick_name", nullable = false ,unique = true)
    private String nickName;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "phone_No", nullable = false,unique = true)
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

   }
