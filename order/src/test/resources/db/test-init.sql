SET FOREIGN_KEY_CHECKS = 0;
truncate table orders;
truncate table order_product;
SET FOREIGN_KEY_CHECKS = 1;

insert into orders
(order_id, customer_id, coupon_id,status, total_price, created_date, last_modified_date,
 shipping_zip_code, shipping_addr1, shipping_addr2, shipping_message, receiver_name, receiver_phone )
values ( 'orderId1', 'customer1', 'couponId1', 'PAYMENT_WAITING', 10000, '2023-12-13 17:09:39.114218'
       , '2023-12-13 17:09:39.115218',
        'zipcode','address1','address2','fast','myName','010-1111-1111');
insert into order_product (order_id, product_id, amounts, price, quantity)
values ('orderId1', 'productId1', 10000, 1000, 10);

insert into orders
(order_id, customer_id, coupon_id, status, total_price, created_date, last_modified_date,
 shipping_zip_code, shipping_addr1, shipping_addr2, shipping_message, receiver_name, receiver_phone )
values ( 'orderId2', 'customer1', 'couponId1','PREPARING', 23000, '2023-12-13 17:09:39.114218'
       , '2023-12-13 17:09:39.115218',
         'zipcode','address1','address2','fast','myName','010-1111-1111');
insert into order_product (order_id, product_id, amounts, price, quantity)
values ('orderId2', 'productId1', 3000, 1000, 3);
insert into order_product (order_id, product_id, amounts, price, quantity)
values ('orderId2', 'productId2', 20000, 2000, 10);

insert into orders
(order_id, customer_id, status, total_price, created_date, last_modified_date,
 shipping_zip_code, shipping_addr1, shipping_addr2, shipping_message, receiver_name, receiver_phone )
values ('orderId3', 'customer1', 'DELIVERY_COMPLETED', 45000, '2023-12-13 17:09:39.114218',
        '2023-12-13 17:09:39.115218',
        'zipcode','address1','address2','fast','myName','010-1111-1111');
insert into order_product (order_id, product_id, amounts, price, quantity)
values ('orderId3', 'productId3', 45000, 4500, 10);