SET FOREIGN_KEY_CHECKS = 0;
truncate table promotion;
truncate table coupon;
SET FOREIGN_KEY_CHECKS = 1;


insert into promotion values ('promotion1', 'title1', 'context1', 2, '2023-10-28','2023-10-29','TEN_PERCENTAGE');
insert into promotion values ('promotion2', 'title2', 'context2', 2, '2023-10-30','2024-11-30','TEN_PERCENTAGE');
insert into promotion values ('promotion3', 'title3', 'context3', 2, '2023-10-31','2024-11-30','TEN_PERCENTAGE');
insert into promotion values ('promotion4', 'title3', 'context3', 10, '2023-10-31','2024-11-30','TEN_PERCENTAGE');
insert into coupon values ('coupon1', 'promotion1', null, 'TEN_PERCENTAGE', '2025-12-31', null, 'CREATED');
insert into coupon values ('coupon2', 'promotion1', null, 'TEN_PERCENTAGE', '2023-12-31', null, 'CREATED');
insert into coupon values ('coupon3', 'promotion2', null, 'TEN_PERCENTAGE', '2023-12-31', null, 'CREATED');
insert into coupon values ('coupon4', 'promotion2', null, 'TEN_PERCENTAGE', '2023-12-31', null, 'CREATED');
insert into coupon values ('coupon5', 'promotion3', null, 'TEN_PERCENTAGE', '2023-12-31', null, 'CREATED');
insert into coupon values ('coupon6', 'promotion3', '2023-10-31', 'TEN_PERCENTAGE', '2025-12-31', '1', 'ISSUED');
insert into coupon values ('coupon7',  'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon8',  'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon9',  'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon10', 'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon11', 'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon12', 'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon13', 'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon14', 'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon15', 'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');
insert into coupon values ('coupon16', 'promotion4', null, 'TEN_PERCENTAGE', '2024-12-31', null, 'CREATED');