
drop table if exists user_followings;
drop table if exists comments;
drop table if exists favorites;
drop table if exists post;
drop table if exists users;
drop table if exists topic_tags;
CREATE table if not exists users
(
    user_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    email     varchar(255) not null,
    password  varchar(255) not null,
    name      varchar(20)  not null,
    nick_name varchar(20)  not null,
    phone_no  varchar(20)  not null,
    gender    varchar(10)  not null,
    CONSTRAINT unique_email_nick_name_phone_no UNIQUE (email, nick_name, phone_no)
    );
create table if not exists topic_tags
(
    topic_id   bigint primary key auto_increment,
    topic_body varchar(255) not null unique
    );
create table if not exists post
(
    post_id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    author_id         BIGINT         not null,
    title             varchar(255)   not null,
    body              varchar(11000) not null,
    nick_name         varchar(20)    not null,
    post_hits         BIGINT,
    image_upload_name varchar(255),
    post_topic        Bigint         not null,
    created_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users foreign key (author_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_post_topic foreign key (post_topic) references topic_tags(topic_id) ON DELETE CASCADE
    );
drop table if exists comments;

create table if not exists comments
(
    comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    author_id  BIGINT       NOT NULL,
    post_id    BIGINT       NOT NULL,
    body       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_author FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE
    );
create table if not exists favorites
(
    favorites_id      BIGINT primary key AUTO_INCREMENT,
    post_id           BIGINT not null,
    favorites_user_id BIGINT not null unique,
    constraint fk_like_user FOREIGN KEY (favorites_user_id) REFERENCES users (user_id) ON DELETE cascade,
    constraint fk_like_post FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE
    );
CREATE TABLE IF NOT EXISTS user_followings
(
    followings_id bigint primary key AUTO_INCREMENT,
    follower_id   BIGINT NOT NULL,
    followee_id   BIGINT NOT NULL,
    CONSTRAINT fk_follower FOREIGN KEY (follower_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_followee FOREIGN KEY (followee_id) REFERENCES users (user_id) ON DELETE CASCADE
    );



