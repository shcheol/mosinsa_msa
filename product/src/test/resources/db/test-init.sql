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

insert into category (category_id, name)
values ('categoryId1', '상의'),
       ('categoryId2', '바지'),
       ('categoryId3', '아우터'),
       ('categoryId4', '신발'),
       ('categoryId5', '가방'),
       ('categoryId6', '소품');
insert into category (category_id, name, parent_id)
values ('categoryId7', '맨투맨/스웨트', 'categoryId1'),
       ('categoryId8', '셔츠/블라우스', 'categoryId1'),
       ('categoryId9', '후드 티셔츠', 'categoryId1'),
       ('categoryId10', '니트/스웨터', 'categoryId1'),
       ('categoryId11', '데님 팬츠', 'categoryId2'),
       ('categoryId12', '코튼 팬츠', 'categoryId2'),
       ('categoryId13', '트레이닝/조거 팬츠', 'categoryId2'),
       ('categoryId14', '숏 팬츠', 'categoryId2'),
       ('categoryId15', '블루종', 'categoryId3'),
       ('categoryId16', '레더 재킷', 'categoryId3'),
       ('categoryId17', '무스탕', 'categoryId3'),
       ('categoryId18', '트러커 재킷', 'categoryId3'),
       ('categoryId19', '스니커즈', 'categoryId4'),
       ('categoryId20', '구두', 'categoryId4'),
       ('categoryId21', '샌들', 'categoryId4'),
       ('categoryId22', '스포츠화', 'categoryId4'),
       ('categoryId23', '백팩', 'categoryId5'),
       ('categoryId24', '메신저 백', 'categoryId5'),
       ('categoryId25', '숄더백', 'categoryId5'),
       ('categoryId26', '토트백', 'categoryId5'),
       ('categoryId27', '모자', 'categoryId6'),
       ('categoryId28', '양말/레그웨어', 'categoryId6'),
       ('categoryId29', '선글라스/안경테', 'categoryId6'),
       ('categoryId30', '시계', 'categoryId6');

insert into category (category_id, name, parent_id)
values ('categoryId31', '슬립온', 'categoryId19'),
       ('categoryId32', '캔버스', 'categoryId19'),
       ('categoryId33', '뮬', 'categoryId19'),
       ('categoryId34', '더비슈즈', 'categoryId20'),
       ('categoryId35', '윙 팁', 'categoryId20'),
       ('categoryId36', '로퍼', 'categoryId20'),
       ('categoryId37', '쪼리', 'categoryId21'),
       ('categoryId38', '클로크', 'categoryId21'),
       ('categoryId39', '스트랩 샌들', 'categoryId21'),
       ('categoryId40', '트레킹화', 'categoryId22'),
       ('categoryId41', '축구화', 'categoryId22'),
       ('categoryId42', '아쿠아슈즈', 'categoryId22'),
       ('categoryId43', '야구모자', 'categoryId27'),
       ('categoryId44', '페도라', 'categoryId27'),
       ('categoryId45', '비니', 'categoryId27'),
       ('categoryId46', '양말', 'categoryId28'),
       ('categoryId47', '스타킹', 'categoryId28'),
       ('categoryId48', '안경', 'categoryId29'),
       ('categoryId49', '선글라스', 'categoryId29'),
       ('categoryId50', '안경소품', 'categoryId29'),
       ('categoryId51', '디지털', 'categoryId30'),
       ('categoryId52', '아날로그', 'categoryId30'),
       ('categoryId53', '시계용품', 'categoryId30');

insert into stock(total, status, created_date, last_modified_date)
values ( 10, 'ON', '2024-05-25 12:20:35', '2024-05-25 12:20:35'),
       ( 20, 'SOLD_OUT', '2024-05-25 12:20:35', '2024-05-25 12:20:35'),
       ( 30, 'SOLD_OUT', '2024-05-25 12:20:35', '2024-05-25 12:20:35'),
       ( 30, 'ON', '2024-05-25 12:20:35', '2024-05-25 12:20:35'),
       ( 30, 'ON', '2024-05-25 12:20:35', '2024-05-25 12:20:35');
insert into product (product_id, name, price, category_id, stock_id, created_date, last_modified_date)
values ('productId1', '반팔', 2000, 'categoryId1', 1, '2024-05-25 12:20:35', '2024-05-25 12:20:35'),
       ('productId2', '청바지', 1000, 'categoryId2', 2, '2024-05-25 15:20:35', '2024-05-25 12:20:35'),
       ('productId3', '코트', 3000, 'categoryId3', 3, '2024-05-25 15:21:35', '2024-05-25 12:20:35'),
       ('productId4', '비슬로우', 62910, 'categoryId1', 4, '2024-06-25 15:21:35', '2024-05-25 12:20:35'),
       ('productId5', '검정반팔', 2000, 'categoryId1', 5, '2024-02-25 15:21:35', '2024-05-25 12:20:35');



insert into review
values ('reviewId1', 'good', '2024-02-25 15:21:35', 'N', 'writer1', 'writerId1', 'productId1', 4),
       ('reviewId2', 'good', '2024-02-25 15:22:35', 'Y', 'writer2', 'writerId2', 'productId1', 0);
insert into comment
values ('commentId1', 'good', '2024-02-25 16:21:35', 'N', 'writer3', 'writerId3', 'reviewId1'),
       ('commentId2', 'good', '2024-02-25 16:22:35', 'N', 'writer1', 'writerId1', 'reviewId1'),
       ('commentId3', 'good', '2024-02-25 16:23:35', 'Y', 'writer2', 'writerId2', 'reviewId1'),
       ('commentId4', 'good', '2024-02-25 16:24:35', 'N', 'writer1', 'writerId1', 'reviewId1');

insert into reaction(reaction_id, active, created_date, last_modified_date, member_id, reaction_type, target_id,
                     target_type)
values ('reactionId0', 'Y', '2024-02-25 16:24:35', '2024-02-25 16:24:34', 'memberId1', 'LIKES', 'productId4', 'PRODUCT'),
       ('reactionId1', 'N', '2024-02-25 16:24:35', '2024-02-25 16:24:35', 'memberId1', 'LIKES', 'productId1', 'PRODUCT'),
       ('reactionId2', 'Y', '2024-02-25 16:24:35', '2024-02-25 16:24:36', 'memberId2', 'LIKES', 'productId1', 'PRODUCT'),
       ('reactionId3', 'Y', '2024-02-25 16:24:35', '2024-02-25 16:24:37', 'memberId2', 'LIKES', 'productId2', 'PRODUCT'),
       ('reactionId4', 'N', '2024-02-25 16:24:35', '2024-02-25 16:24:38', 'memberId2', 'LIKES', 'productId3', 'PRODUCT'),
       ('reactionId5', 'N', '2024-02-25 16:24:35', '2024-02-25 16:24:39', 'memberId2', 'LIKES', 'reviewId1', 'REVIEW');