alter table order_product
    drop
        foreign key FKl5mnj9n0di7k1v90yxnthkc73;

drop table if exists order_product;

drop table if exists orders;

create table order_product
(
    order_id   varchar(255) not null,
    amounts    integer,
    price      integer,
    product_id varchar(255),
    quantity   integer      not null
) engine = InnoDB;

create table orders
(
    id                 varchar(255) not null,
    created_date       datetime(6),
    last_modified_date datetime(6),
    customer_id        varchar(255),
    status             varchar(255),
    total_price        integer,
    primary key (id)
) engine = InnoDB;

alter table order_product
    add constraint FKl5mnj9n0di7k1v90yxnthkc73
        foreign key (order_id)
            references orders (id);