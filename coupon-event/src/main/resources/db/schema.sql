SET FOREIGN_KEY_CHECKS = 0;
drop table if exists promotion;
drop table if exists promotion_condition;
drop table if exists promotion_condition_option;
drop table if exists promotion_history;
drop table if exists quest;
drop table if exists coupon;
drop table if exists coupon_group;
drop table if exists coupon_group_info;

SET FOREIGN_KEY_CHECKS = 1;

create table coupon
(
    coupon_id       varchar(255) not null,
    discount_policy varchar(255),
    during_date     date,
    min_use_price   bigint,
    issued_date     datetime(6),
    member_id       varchar(255),
    state           varchar(255),
    INDEX coupon_member_idx (member_id),
    primary key (coupon_id)
) engine = InnoDB;

create table coupon_group
(
    id                 bigint not null auto_increment,
    name               varchar(255),
    discount_policy    varchar(255),
    min_use_price      bigint,
    during_date        date,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table coupon_group_info
(
    id                    bigint  not null auto_increment,
    coupon_group_sequence bigint  not null,
    discount_policy       varchar(255),
    quantity              integer not null,
    quest_id              bigint,
    created_date          datetime(6),
    last_modified_date    datetime(6),
    primary key (id)
) engine = InnoDB;

create table promotion
(
    promotion_id           varchar(255) not null,
    title                  varchar(255),
    contexts               varchar(255),
    date_unit              varchar(255),
    end_date               datetime(6),
    start_date             datetime(6),
    promotion_condition_id bigint,
    primary key (promotion_id)
) engine = InnoDB;

create table promotion_condition
(
    id                 bigint not null auto_increment,
    conditions         varchar(255),
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table promotion_condition_option
(
    id                     bigint not null auto_increment,
    option_name            varchar(255),
    promotion_condition_id bigint,
    created_date           datetime(6),
    last_modified_date     datetime(6),
    primary key (id)
) engine = InnoDB;

create table promotion_history
(
    id                 bigint not null auto_increment,
    member_id          varchar(255),
    quest_id           bigint,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table quest
(
    id                            bigint not null auto_increment,
    promotion_id                  varchar(255),
    promotion_condition_option_id bigint,
    created_date                  datetime(6),
    last_modified_date            datetime(6),
    primary key (id)
) engine = InnoDB;