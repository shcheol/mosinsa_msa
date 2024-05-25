SET FOREIGN_KEY_CHECKS = 0;
drop table if exists likes;
drop table if exists stock;
drop table if exists product;
drop table if exists product_likes;
drop table if exists likes_member;
drop table if exists category;
SET FOREIGN_KEY_CHECKS = 1;

create table category
(
    category_id varchar(255) not null,
    name        varchar(255),
    primary key (category_id)
) engine = InnoDB;

create table likes
(
    likes_id varchar(255) not null,
    total    integer,
    primary key (likes_id)
) engine = InnoDB;

create table likes_member
(
    likes_member_id        varchar(255) not null,
    member_id varchar(255),
    likes_id   varchar(255),
    UNIQUE INDEX likes_member_index(likes_id, member_id),
    primary key (likes_member_id)
) engine = InnoDB;

create table product
(
    product_id  varchar(255) not null,
    name        varchar(255),
    price       integer,
    category_id varchar(255),
    likes_id    varchar(255),
    stock_id    varchar(255),
    created_date timestamp,
    primary key (product_id)
) engine = InnoDB;

create table stock
(
    id     varchar(255) not null,
    remain bigint       not null,
    primary key (id)
) engine = InnoDB;
