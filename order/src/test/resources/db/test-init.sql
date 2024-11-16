SET FOREIGN_KEY_CHECKS = 0;
truncate table orders;
truncate table product_option;
truncate table order_product;
truncate table order_coupon;
SET FOREIGN_KEY_CHECKS = 1;

insert into orders
(order_id, customer_id, status, total_price, created_date, last_modified_date, shipping_zip_code, shipping_addr1, shipping_addr2, shipping_message, receiver_name, receiver_phone)
values ('orderId1', 'customer1', 'PAYMENT_WAITING', 10000, '2023-12-13 17:09:39.114218'           , '2023-12-13 17:09:39.115218',        'zipcode', 'address1', 'address2', 'fast', 'myName', '010-1111-1111'),
       ('orderId2', 'customer1', 'PREPARING', 23000, '2023-12-14 17:09:39.114218', '2023-12-13 17:09:39.115218',        'zipcode', 'address1', 'address2', 'fast', 'myName', '010-1111-1111'),
       ('orderId3', 'customer2', 'DELIVERY_COMPLETED', 45000, '2023-12-15 17:09:39.114218',        '2023-12-13 17:09:39.115218',        'zipcode', 'address1', 'address2', 'fast', 'myName', '010-1111-1111'),
       ('orderId4', 'customer3', 'SHIPPED', 10000, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218',        'zipcode', 'address1', 'address2', 'fast', 'myName', '010-1111-1111'),
       ('orderId5', 'customer3', 'DELIVERING', 10000, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218',        'zipcode', 'address1', 'address2', 'fast', 'myName', '010-1111-1111');
insert into order_product (id, order_id, product_id, name, amounts, price, quantity, created_date, last_modified_date)
values (1, 'orderId1', 'productId1', 'productName', 10000, 1000, 10, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (2, 'orderId2', 'productId1', 'productName', 3000, 1000, 3, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (3, 'orderId2', 'productId2', 'productName', 20000, 2000, 10, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (4, 'orderId3', 'productId3', 'productName', 45000, 4500, 10, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (5, 'orderId4', 'productId1', 'productName', 10000, 1000, 10, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (6, 'orderId5', 'productId1', 'productName', 10000, 1000, 10, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218');
insert into product_option(id, order_product_id, option_id, name, created_date, last_modified_date)
values (1,1,1,'optionName', '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (2,2,1,'optionName', '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (3,3,2,'optionName', '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (4,4,3,'optionName', '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (5,5,1,'optionName', '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (6,6,1,'optionName', '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218');

insert into order_coupon(id, coupon_id,discount_policy, order_product_id, created_date, last_modified_date)
values (1, 'couponId1','PERCENT_10', 1, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (2, 'couponId2','PERCENT_10', 2, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218'),
       (3, 'couponId3','PERCENT_10', 4, '2023-12-12 17:09:39.114218', '2023-12-13 17:09:39.115218')