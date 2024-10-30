SET FOREIGN_KEY_CHECKS = 0;
drop table if exists product;
drop table if exists product_option;
drop table if exists product_options_value;
drop table if exists brand;
drop table if exists stock;
drop table if exists stock_history;
drop table if exists category;
drop table if exists comment;
drop table if exists review;
drop table if exists reaction;
drop table if exists reaction_info;
SET FOREIGN_KEY_CHECKS = 1;

create table category
(
    category_id varchar(255)        not null,
    name        varchar(255) unique not null,
    parent_id   varchar(255),
    primary key (category_id)
) engine = InnoDB;
create table brand
(
    id                 bigint not null auto_increment,
    name               varchar(255),
    description        varchar(255),
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;
create table product
(
    product_id         varchar(255) not null,
    name               varchar(255),
    price              integer,
    category_id        varchar(255),
    brand_id           bigint,
    created_date       timestamp,
    last_modified_date timestamp,
    primary key (product_id)
) engine = InnoDB;
create table product_options
(
    id                 bigint not null auto_increment,
    option_name        varchar(255),
    product_id         varchar(255),
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;
create table product_options_value
(
    id                 bigint not null auto_increment,
    change_price       integer,
    change_type        varchar(255),
    options_value              varchar(255),
    product_option_id  bigint,
    stock_id           bigint,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;
create table stock
(
    id                 bigint       not null auto_increment,
    total              bigint       not null,
    status             varchar(255) not null,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table stock_history
(
    id                 bigint       not null auto_increment,
    order_num          varchar(255) not null,
    member_id          varchar(255) not null,
    target_id          bigint,
    quantity           bigint,
    type               varchar(255) not null,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table comment
(
    comment_id   varchar(255) not null,
    contents     varchar(255),
    created_date datetime(6),
    deleted      varchar(255) not null,
    writer_name  varchar(255),
    writer_id    varchar(255),
    review_id    varchar(255),
    primary key (comment_id)
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
    primary key (review_id)
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

create table reaction_info
(
    reaction_info_id varchar(255) not null,
    reaction_type    varchar(255),
    target_id        varchar(255),
    target_type      varchar(255),
    total            bigint,
    primary key (reaction_info_id)
) engine = InnoDB;