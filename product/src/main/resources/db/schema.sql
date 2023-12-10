create table if not exists likes
(
    product_id varchar(255) not null,
    member_id  varchar(255)
) engine = InnoDB;

create table if not exists product
(
    product_id varchar(255) not null,
    name       varchar(255),
    value      integer      not null,
    stock_id   varchar(255),
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