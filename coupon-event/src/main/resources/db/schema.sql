SET FOREIGN_KEY_CHECKS = 0;
drop table if exists promotion;
drop table if exists coupon;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS promotion (
  promotion_id VARCHAR(255) NOT NULL PRIMARY KEY,
  title VARCHAR(100),
  contexts TEXT,
  stock INT(4),
  start_date DATE,
  end_date DATE,
  discount_policy VARCHAR(255)
) engine=InnoDB;
CREATE TABLE IF NOT EXISTS coupon (
  coupon_id VARCHAR(255) NOT NULL PRIMARY KEY,
  promotion_id VARCHAR(255),
  issued_date DATE,
  discount_policy VARCHAR(255),
  during_date DATE,
  member_id VARCHAR(255),
  state VARCHAR(100),
  UNIQUE INDEX coupon_promotion_member_idx(promotion_id, member_id),
  INDEX coupon_member_idx(member_id)
) engine=InnoDB;