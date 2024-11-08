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
values ('promotion1', '최대 10000원 쿠폰 증정', '신규 가입고객 10000원, 기존 고객 3000원 쿠폰', 'ONCE', '2023-10-28', '2025-10-29', 1),
       ('promotion2', '컴백 이벤트', '신규 고객이나 3개월 주문이력이 없는 고객에게 10% 쿠폰', 'ONCE', '2023-10-31', '2024-11-30', 2),
       ('promotion3', '매일 랜덤 쿠폰이벤트 ', '꽝없는 행운, 매일 1000, 3000, 10000원 쿠폰을 랜덤으로', 'DAILY', '2023-10-31', '2024-11-30', 3);

insert into promotion_condition(id, conditions, created_date, last_modified_date)
values (1, 'NEW_OR_OLD_MEMBER', '2023-10-31', '2024-11-30'),
       (2, 'COMEBACK', '2023-10-31', '2024-11-30'),
       (3, 'RANDOM_COUPON', '2023-10-31', '2024-11-30');

insert into promotion_condition_option(id, option_name, promotion_condition_id, created_date, last_modified_date)
values (1, 'NEW_MEMBER', 1, '2023-10-31', '2024-11-30'),
       (2, 'OLD_MEMBER', 1, '2023-10-31', '2024-11-30'),
       (3, 'NEW_MEMBER', 2, '2023-10-31', '2024-11-30'),
       (4, 'COMEBACK', 2, '2023-10-31', '2024-11-30'),
       (5, 'OLD_MEMBER', 2, '2023-10-31', '2024-11-30'),
       (6, 'EVERY_MEMBER', 3, '2023-10-31', '2024-11-30');

insert into quest(id, promotion_id, promotion_condition_option_id, created_date, last_modified_date)
values (1, 'promotion1', 1, '2023-10-31', '2024-11-30'),
       (2, 'promotion1', 2, '2023-10-31', '2024-11-30'),
       (3, 'promotion2', 3, '2023-10-31', '2024-11-30'),
       (4, 'promotion2', 4, '2023-10-31', '2024-11-30'),
       (5, 'promotion2', 5, '2023-10-31', '2024-11-30'),
       (6, 'promotion3', 6, '2023-10-31', '2024-11-30');

insert into coupon_group_info(id, coupon_group_sequence, discount_policy, quantity, quest_id, created_date,
                              last_modified_date)
values (1, 1, 'WON_10000', 300, 1, '2023-10-31', '2024-11-30'),
       (2, 2, 'WON_3000', 300, 2, '2023-10-31', '2024-11-30'),
       (3, 3, 'PERCENT_10', 300, 3, '2023-10-31', '2024-11-30'),
       (4, 3, 'PERCENT_10', 300, 4, '2023-10-31', '2024-11-30'),
       (5, 4, 'WON_1000', 300, 5, '2023-10-31', '2024-11-30'),
       (6, 2, 'WON_3000', 300, 5, '2023-10-31', '2024-11-30'),
       (7, 1, 'WON_10000', 300, 5, '2023-10-31', '2024-11-30');

insert into promotion_history(id, member_id, quest_id, created_date, last_modified_date)
values (1, 'memberId1', 1, '2023-10-31', '2024-11-30'),
       (2, 'memberId2', 1, '2023-10-31', '2024-11-30');

insert into coupon_group(id, name, discount_policy, min_use_price, during_date, created_date, last_modified_date)
values (1, '10%', 'PERCENT_10', 3000, '2024-11-30', '2023-10-31', '2024-11-30'),
       (2, '1000원', 'WON_1000', 3000, '2024-11-30', '2023-10-31', '2024-11-30'),
       (3, '3000원', 'WON_3000', 3000, '2024-11-30', '2023-10-31', '2024-11-30'),
       (4, '10000원', 'WON_10000', 3000, '2024-11-30', '2023-10-31', '2024-11-30');

insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon1', 'memberId1', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon2', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon3', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon4', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');