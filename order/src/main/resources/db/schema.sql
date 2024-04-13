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
    order_id           varchar(255) not null,
    customer_id        varchar(255),
    coupon_id          varchar(255),
    status             varchar(255),
    total_price        integer,
    created_date       datetime(6),
    last_modified_date datetime(6),
    shipping_zip_code varchar(6),
    shipping_addr1 varchar(100),
    shipping_addr2 varchar(100),
    shipping_message varchar(200),
    receiver_name varchar(50),
    receiver_phone varchar(50),
    INDEX order_customer_idx(customer_id),
    primary key (order_id)
) engine = InnoDB;