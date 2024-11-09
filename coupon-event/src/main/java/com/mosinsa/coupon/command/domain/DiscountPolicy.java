package com.mosinsa.coupon.command.domain;

public enum DiscountPolicy {
    NONE{
        @Override
        protected int applyDiscountPrice(int value) {
            return 0;
        }
    }, PERCENT_10{
        @Override
        protected int applyDiscountPrice(int value) {
            return value * 10 / 100;
        }
    }, PERCENT_20 {
        @Override
        protected int applyDiscountPrice(int value) {
            return value * 20 / 100;
        }
    }, WON_10000 {
		@Override
		protected int applyDiscountPrice(int value) {
			return value * 20 / 100;
		}
	}, WON_3000 {
		@Override
		protected int applyDiscountPrice(int value) {
			return value * 20 / 100;
		}
	}, WON_1000 {
		@Override
		protected int applyDiscountPrice(int value) {
			return value * 20 / 100;
		}
	};

    protected abstract int applyDiscountPrice(int value);

}
