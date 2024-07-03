SET FOREIGN_KEY_CHECKS = 0;
drop table if exists product;
drop table if exists stock;
drop table if exists product;
drop table if exists category;
drop table if exists comment_likes;
drop table if exists comment_dislikes;
drop table if exists comment;
drop table if exists review;
drop table if exists review_likes;
drop table if exists review_dislikes;
drop table if exists reaction;
SET FOREIGN_KEY_CHECKS = 1;

create table category
(
    category_id varchar(255) not null,
    name        varchar(255),
    primary key (category_id)
) engine = InnoDB;

create table product
(
    product_id   varchar(255) not null,
    name         varchar(255),
    price        integer,
    category_id  varchar(255),
    stock_id     varchar(255),
    created_date timestamp,
    primary key (product_id)
) engine = InnoDB;

create table stock
(
    id     varchar(255) not null,
    remain bigint       not null,
    primary key (id)
) engine = InnoDB;

create table comment
(
    comment_id     varchar(255) not null,
    contents       varchar(255),
    created_date   datetime(6),
    deleted        varchar(255) not null,
    dislikes_count bigint,
    likes_count    bigint,
    writer_name    varchar(255),
    writer_id      varchar(255),
    review_id      varchar(255),
    primary key (comment_id)
) engine = InnoDB;
create table comment_dislikes
(
    comment_dislikes_id varchar(255) not null,
    member_id           varchar(255) not null,
    comment_id          varchar(255) not null,
    UNIQUE INDEX dislikes_comment_member_index (comment_id, member_id),
    primary key (comment_dislikes_id)
) engine = InnoDB;
create table comment_likes
(
    comment_likes_id varchar(255) not null,
    member_id        varchar(255) not null,
    comment_id       varchar(255) not null,
    UNIQUE INDEX likes_comment_member_index (comment_id, member_id),
    primary key (comment_likes_id)
) engine = InnoDB;
create table review
(
    review_id      varchar(255) not null,
    contents       varchar(255),
    created_date   datetime(6),
    deleted        varchar(255),
    writer_name    varchar(255),
    writer_id      varchar(255),
    product_id     varchar(255),
    comments_count bigint,
    dislikes_count bigint,
    likes_count    bigint,
    primary key (review_id)
) engine = InnoDB;
create table review_dislikes
(
    review_dislikes_id varchar(255) not null,
    member_id          varchar(255) not null,
    review_id          varchar(255) not null,
    UNIQUE INDEX dislikes_review_member_index (review_id, member_id),
    primary key (review_dislikes_id)
) engine = InnoDB;
create table review_likes
(
    review_likes_id varchar(255) not null,
    member_id       varchar(255) not null,
    review_id       varchar(255) not null,
    UNIQUE INDEX likes_review_member_index (review_id, member_id),
    primary key (review_likes_id)
) engine = InnoDB;

create table reaction
(
    reaction_id        varchar(255) not null,
    active             varchar(255) not null,
    created_date       datetime,
    last_modified_date datetime,
    member_id          varchar(255),
    reaction_type      varchar(255),
    target_id          varchar(255),
    target_type        varchar(255),
    primary key (reaction_id)
) engine = InnoDB;