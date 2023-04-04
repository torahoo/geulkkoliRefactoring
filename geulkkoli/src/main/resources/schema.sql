
CREATE table if not exists users
(
    user_id   BIGINT PRIMARY KEY,
    email     varchar(255) not null,
    password  varchar(255) not null,
    name      varchar(20)  not null,
    nick_name varchar(20)  not null,
    gender    varchar(10)  not null,
    CONSTRAINT unique_email_nick_name UNIQUE (email, nick_name)
);

create table if not exists post
(
    post_id           BIGINT PRIMARY KEY,
    author_id         BIGINT         not null,
    title             varchar(255)   not null,
    body              varchar(11000) not null,
    nick_name         varchar(20)    not null,
    image_upload_name varchar(255),
    created_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users foreign key (author_id) REFERENCES users (user_id)
);
drop table if exists comments;

create table if not exists comments
(
    comment_id BIGINT PRIMARY KEY,
    author_id  BIGINT       NOT NULL,
    post_id    BIGINT       NOT NULL,
    body       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_author FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE
);