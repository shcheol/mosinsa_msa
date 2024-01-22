create table if not exists customer
(
    customer_id varchar(255) not null,
    login_id    varchar(255) not null unique,
    password    varchar(255),
    name        varchar(255),
    email       varchar(255),
    grade       varchar(255),
    city        varchar(255),
    street      varchar(255),
    zipcode     varchar(255),
    primary key (customer_id)
) engine = InnoDB;