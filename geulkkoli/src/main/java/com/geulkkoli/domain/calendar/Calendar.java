package com.geulkkoli.domain.calendar;

import com.geulkkoli.domain.post.ConfigDate;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Calendar extends ConfigDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // boolean?? int?? 하....
    @Column(nullable = false)
    private boolean postActive;

    @Column(nullable = false)
    private int posts;


    @Override
    public boolean equals(Object obj) {
       Calendar calendarCheck = (Calendar) obj;
       return getCalendarId() != null && Objects.equals(getCalendarId(), calendarCheck.getCalendarId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }




//    public void addAuthor (User user) {
//        this.user = user;
//        user.getCalendar().
//    }

    public void addPost (Post post) {
        this.post = post;
        post.getCalendar().add(this);
    }

    //출석체크를 한다.
    public void CheckActive (boolean postActive) {
        this.postActive = postActive;
    }

    //날짜체크를 한다.
    public void CheckDate(int posts) {
        this.posts = posts;
    }
}
