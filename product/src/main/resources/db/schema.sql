SET FOREIGN_KEY_CHECKS = 0;
drop table likes;
drop table stock;
drop table product;
drop table category;
SET FOREIGN_KEY_CHECKS = 1;


create table if not exists likes
(
    likes_id   varchar(255) not null,
    member_id  varchar(255),
    product_id varchar(255),
    primary key (likes_id)
) engine = InnoDB;

create table if not exists product
(
    product_id  varchar(255) not null,
    name        varchar(255),
    price       integer,
    category_id varchar(255),
    stock_id    varchar(255),
    primary key (product_id)
) engine = InnoDB;

create table if not exists stock
(
    id     varchar(255) not null,
    remain bigint       not null,
    primary key (id)
) engine = InnoDB;

alter table likes
    add constraint FKbt06f6qrlpr663ng2qlb0gvu8
        foreign key (product_id)
            references product (product_id);

alter table product
    add constraint
        foreign key (stock_id)
            references stock (id);

alter table likes
    add constraint FKbt06f6qrlpr663ng2qlb0gvu8
        foreign key (product_id)
            references product (product_id);

alter table product
    add constraint FK1mtsbur82frn64de7balymq9s
        foreign key (category_id)
            references category (category_id);

alter table product
    add constraint FK90w0j7y2y7chsmk4v4k02ekqf
        foreign key (stock_id)
            references stock (id);