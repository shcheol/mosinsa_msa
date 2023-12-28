CREATE TABLE IF NOT EXISTS promotion (
  promotion_id VARCHAR(255) NOT NULL PRIMARY KEY,
  title VARCHAR(100),
  contexts TEXT,
  quantity INT(4),
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
  INDEX coupon_promotion_idx(promotion_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS member (
 member_id VARCHAR(255) NOT NULL PRIMARY KEY
) engine=InnoDB;
