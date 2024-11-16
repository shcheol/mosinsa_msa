SET FOREIGN_KEY_CHECKS = 0;
drop table if exists product_option;
drop table if exists order_product;
drop table if exists order_coupon;
drop table if exists orders;
SET FOREIGN_KEY_CHECKS = 1;

create table product_option
(
    id                 bigint not null auto_increment,
    name               varchar(255),
    order_product_id   varchar(255),
    option_id          bigint,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table order_product
(
    id                 bigint  not null auto_increment,
    name               varchar(255),
    order_id           varchar(255),
    amounts            integer,
    price              integer,
    product_id         varchar(255),
    quantity           integer not null,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table order_coupon
(
    id                 bigint not null auto_increment,
    coupon_id          varchar(255),
    discount_policy    varchar(255),
    order_product_id   bigint,
    created_date       datetime(6),
    last_modified_date datetime(6),
    primary key (id)
) engine = InnoDB;

create table orders
(
    order_id           varchar(255) not null,
    customer_id        varchar(255),
    status             varchar(255),
    total_price        integer,
    shipping_zip_code  varchar(100),
    shipping_addr1     varchar(100),
    shipping_addr2     varchar(100),
    shipping_message   varchar(200),
    receiver_name      varchar(50),
    receiver_phone     varchar(50),
    created_date       datetime(6),
    last_modified_date datetime(6),
    INDEX order_customer_idx (customer_id),
    primary key (order_id)
) engine = InnoDB;