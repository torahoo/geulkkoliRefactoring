package com.geulkkoli.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/*Db에서 관리해야하는 postId를 Setter로 값 주입할 수 있는 코드라서 @Setter 지움*/

@NoArgsConstructor
@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postNo;

    //칼럼에 널 값이 들어오는 걸 허용하지 않음
    @Column(nullable = false)
    private String title;
    @Column(name = "body", nullable = false)
    private String postBody;
    @Column(nullable = false)
    private String nickName;

    @Builder
    public Post(String title, String postBody, String nickName) {
        this.title = title;
        this.postBody = postBody;
        this.nickName = nickName;
    }

    //제목을 바꾼다.
    public void changeTitle(String title) {
        this.title = title;
    }

    //본문을 바꾼다.
    public void changePostBody(String updateBody) {
        this.postBody = updateBody;
    }

    //별명을 바꾼다.
    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return getPostNo() != null && Objects.equals(getPostNo(), post.getPostNo());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

