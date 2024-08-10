SET FOREIGN_KEY_CHECKS = 0;
truncate table promotion;
truncate table coupon;
SET FOREIGN_KEY_CHECKS = 1;


insert into promotion(promotion_id, title, contexts, start_date, end_date)
values ('promotion1', 'title1', 'context1', '2023-10-28', '2023-10-29');
insert into promotion(promotion_id, title, contexts, start_date, end_date)
values ('promotion2', 'title2', 'context2', '2023-10-30', '2024-11-30');
insert into promotion(promotion_id, title, contexts, start_date, end_date)
values ('promotion3', 'title3', 'context3', '2023-10-31', '2024-11-30');
insert into promotion(promotion_id, title, contexts, start_date, end_date)
values ('promotion4', 'title3', 'context3', '2023-10-31', '2024-11-30');

insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon1', 'memberId1', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon2', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon3', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');
insert into coupon(coupon_id, member_id, issued_date, discount_policy, during_date, min_use_price, state)
values ('coupon4', 'memberId2', '2023-12-31', 'TEN_PERCENTAGE', '2025-12-31', 3000, 'ISSUED');