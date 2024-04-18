package com.mosinsa.order.command.domain;

public enum DiscountPolicy {
    NONE{
        @Override
		public int applyDiscountPrice(int value) {
            return 0;
        }
    }, TEN_PERCENTAGE{
        @Override
		public int applyDiscountPrice(int value) {
            return value * 10 / 100;
        }
    }, TWENTY_PERCENTAGE {
        @Override
		public int applyDiscountPrice(int value) {
            return value * 20 / 100;
        }
    };

    public abstract int applyDiscountPrice(int value);

}
