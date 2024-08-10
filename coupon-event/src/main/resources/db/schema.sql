SET FOREIGN_KEY_CHECKS = 0;
drop table if exists promotion;
drop table if exists coupon;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS promotion (
  promotion_id VARCHAR(255) NOT NULL PRIMARY KEY,
  title VARCHAR(100),
  contexts TEXT,
  start_date DATE,
  end_date DATE
) engine=InnoDB;
CREATE TABLE IF NOT EXISTS coupon (
  coupon_id VARCHAR(255) NOT NULL PRIMARY KEY,
  member_id VARCHAR(255),
  issued_date DATE,
  discount_policy VARCHAR(255),
  during_date DATE,
  min_use_price bigint,
  state VARCHAR(100),
  INDEX coupon_member_idx(member_id)
) engine=InnoDB;