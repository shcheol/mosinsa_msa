SET FOREIGN_KEY_CHECKS = 0;
truncate table stock;
truncate table stock_history;
truncate table product;
truncate table category;
truncate table review;
truncate table comment;
truncate table reaction;
truncate table reaction_info;
SET FOREIGN_KEY_CHECKS = 1;

insert into category (category_id, name) values ('categoryId1','상의');
insert into category (category_id, name) values ('categoryId2','바지');
insert into category (category_id, name) values ('categoryId3','아우터');
insert into category (category_id, name) values ('categoryId4','신발');
insert into category (category_id, name) values ('categoryId5','가방');
insert into category (category_id, name) values ('categoryId6','양말');
insert into category (category_id, name) values ('categoryId7','원피스');
insert into category (category_id, name) values ('categoryId8','치마');
insert into category (category_id, name) values ('categoryId9','모자');
insert into stock values ('stockId1',10,'ON');
insert into stock values ('stockId2',20,'SOLD_OUT');
insert into stock values ('stockId3',30,'SOLD_OUT');
insert into stock values ('stockId4',30,'ON');
insert into stock values ('stockId5',30,'ON');
insert into product values ('productId1', '반팔', 2000,'categoryId1','stockId1', '2024-05-25 12:20:35');
insert into product values ('productId2', '청바지', 1000,'categoryId2', 'stockId2', '2024-05-25 15:20:35');
insert into product values ('productId3', '코트', 3000,'categoryId3', 'stockId3','2024-05-25 15:21:35');
insert into product values ('productId4', '비슬로우', 62910,'categoryId1', 'stockId4','2024-06-25 15:21:35');
insert into product values ('productId5', '검정반팔', 2000,'categoryId1', 'stockId5','2024-02-25 15:21:35');

insert into review values ('reviewId1', 'good', '2024-02-25 15:21:35', 'N','writer1','writerId1','productId1',4);
insert into review values ('reviewId2', 'good', '2024-02-25 15:22:35', 'Y','writer2','writerId2','productId1',0);
insert into comment values ('commentId1', 'good', '2024-02-25 16:21:35', 'N','writer3','writerId3','reviewId1');
insert into comment values ('commentId2', 'good', '2024-02-25 16:22:35', 'N','writer1','writerId1','reviewId1');
insert into comment values ('commentId3', 'good', '2024-02-25 16:23:35', 'Y','writer2','writerId2','reviewId1');
insert into comment values ('commentId4', 'good', '2024-02-25 16:24:35', 'N','writer1','writerId1','reviewId1');

insert into reaction(reaction_id, active, created_date, last_modified_date, member_id, reaction_type, target_id, target_type)
    values ('reactionId1','N','2024-02-25 16:24:35','2024-02-25 16:24:35','memberId1', 'LIKES','productId1','PRODUCT');
insert into reaction(reaction_id, active, created_date, last_modified_date, member_id, reaction_type, target_id, target_type)
    values ('reactionId2','Y','2024-02-25 16:24:35','2024-02-25 16:24:35','memberId2', 'LIKES','productId1','PRODUCT');
