package com.geulkkoli.domain.post;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postNo;
    private String title;
    private String postBody;
    private String nickName;

    @Builder
    public Post(String title, String postBody, String nickName) {
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
    }
}
