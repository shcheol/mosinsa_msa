SET FOREIGN_KEY_CHECKS = 0;
truncate table promotion;
truncate table promotion_condition;
truncate table promotion_condition_option;
truncate table promotion_history;
truncate table quest;
truncate table coupon;
truncate table coupon_group;
truncate table coupon_group_info;
SET FOREIGN_KEY_CHECKS = 1;

insert into promotion(promotion_id, title, contexts, date_unit, start_date, end_date, promotion_condition_id)
values ('promotion1', 'title1', '신규 가입자 10000원, 기존 유저 3000원 쿠폰', 'ONCE', '2023-10-28', '2025-10-29', 1),
       ('promotion2', 'title2', 'context2', 'ONCE','2023-10-30', '2024-11-30', 1),
       ('promotion3', 'title3', 'context3','DAILY', '2023-10-31', '2024-11-30', 1),
       ('promotion4', 'title3', 'context3', 'DAILY','2023-10-31', '2024-11-30', 2),
       ('promotion5', 'title3', 'context3', 'DAILY','2023-10-31', '2023-11-30', 2);

insert into promotion_condition(id, conditions, created_date, last_modified_date)
values (1, 'NEW_MEMBER', '2023-10-31', '2024-11-30'),
       (2, 'ORDER_COUNT', '2023-10-31', '2024-11-30');

insert into promotion_condition_option(id, option_name, promotion_condition_id, created_date, last_modified_date)
values (1, 'NEW_MEMBER', 1, '2023-10-31', '2024-11-30'),
       (2, 'OLD_MEMBER', 1, '2023-10-31', '2024-11-30'),
       (3, '100000미만', 2, '2023-10-31', '2024-11-30'),
       (4, '100000이상', 2, '2023-10-31', '2024-11-30'),
       (5, '1000000이상', 2, '2023-10-31', '2024-11-30');

insert into quest(id, promotion_id, promotion_condition_option_id, created_date, last_modified_date)
values (1, 'promotion1', 1, '2023-10-31', '2024-11-30'),
       (2, 'promotion1', 2, '2023-10-31', '2024-11-30'),
       (3, 'promotion4', 3, '2023-10-31', '2024-11-30'),
       (4, 'promotion4', 4, '2023-10-31', '2024-11-30'),
       (5, 'promotion4', 5, '2023-10-31', '2024-11-30');

insert into coupon_group_info(id, coupon_group_sequence, discount_policy, quantity, quest_id, created_date, last_modified_date)
values (1, 1, 'TWENTY_PERCENTAGE', 300, 1, '2023-10-31', '2024-11-30'),
       (2, 2, 'TEN_PERCENTAGE', 300, 2, '2023-10-31', '2024-11-30'),
       (3, 3, '3000won', 300, 3, '2023-10-31', '2024-11-30'),
       (4, 3, '5000won', 300, 3, '2023-10-31', '2024-11-30'),
       (5, 4, '10000won', 300, 3, '2023-10-31', '2024-11-30');

insert into coupon_group(id, name, discount_policy, min_use_price, during_date, created_date, last_modified_date)
values (1, '300원', 'TWENTY_PERCENTAGE', 3000,'2024-11-30', '2023-10-31', '2024-11-30'),
       (2, '2', 'TEN_PERCENTAGE', 3000,'2024-11-30', '2023-10-31', '2024-11-30'),
       (3, '3', '3000won', 3000,'2024-11-30', '2023-10-31', '2024-11-30'),
       (4, '3', '5000won', 3000, '2024-11-30','2023-10-31', '2024-11-30'),
       (5, '4', '10000won', 3000, '2024-11-30','2023-10-31', '2024-11-30');

insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon1', 'memberId1', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon2', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon3', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon4', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');