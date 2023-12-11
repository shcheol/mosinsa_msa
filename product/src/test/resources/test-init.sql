SET FOREIGN_KEY_CHECKS = 0;
truncate table likes;
truncate table stock;
truncate table product;
truncate table category;
SET FOREIGN_KEY_CHECKS = 1;

insert into category (category_id, name) values ('categoryId1','상의');
insert into category (category_id, name) values ('categoryId2','하의');
insert into category (category_id, name) values ('categoryId3','아우터');
insert into stock value ('stockId1',10);
insert into stock value ('stockId2',20);
insert into stock value ('stockId3',30);
insert into product values ('productId1', '반팔', 2000,'categoryId1', 'stockId1');
insert into product values ('productId2', '청바지', 1000,'categoryId2', 'stockId2');
insert into product values ('productId3', '코트', 3000,'categoryId3', 'stockId3');