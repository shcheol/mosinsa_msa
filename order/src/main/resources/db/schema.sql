alter table order_product
    drop
        foreign key FKl5mnj9n0di7k1v90yxnthkc73;

drop table if exists order_product;

drop table if exists orders;12

create table if not exists order_product
(
    order_id   varchar(255) not null,
    amounts    integer,
    price      integer,
    product_id varchar(255),
    quantity   integer      not null
) engine = InnoDB;

create table if not exists orders
(
    id                 varchar(255) not null,
    customer_id        varchar(255),
    status             varchar(255),
    total_price        integer,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

alter table order_product
    add constraint FKl5mnj9n0di7k1v90yxnthkc73
        foreign key (order_id)
            references orders (id);