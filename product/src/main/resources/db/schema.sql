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
    id        varchar(255) not null,
    member_id varchar(255),
    like_id   varchar(255),
    primary key (id)
) engine = InnoDB;

create table product
(
    product_id  varchar(255) not null,
    name        varchar(255),
    price       integer,
    category_id varchar(255),
    likes_id    varchar(255),
    stock_id    varchar(255),
    primary key (product_id)
) engine = InnoDB;

create table stock
(
    id     varchar(255) not null,
    remain bigint       not null,
    primary key (id)
) engine = InnoDB;

alter table likes_member
    add constraint FKkpcwhnbcpqe8y92f8oo4ya745
        foreign key (like_id)
            references likes (likes_id);

alter table product
    add constraint FK1mtsbur82frn64de7balymq9s
        foreign key (category_id)
            references category (category_id);

alter table product
    add constraint FKc4d1sffbsfgl23p2n98fboi68
        foreign key (likes_id)
            references likes (likes_id);

alter table product
    add constraint FK90w0j7y2y7chsmk4v4k02ekqf
        foreign key (stock_id)
            references stock (id);