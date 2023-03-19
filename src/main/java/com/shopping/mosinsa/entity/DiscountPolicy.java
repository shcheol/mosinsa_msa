package com.shopping.mosinsa.entity;

public enum DiscountPolicy {

    NONE{
        @Override
        protected int applyDiscountPrice(int value) {
            return 0;
        }
    }, TEN_PERCENTAGE{
        @Override
        protected int applyDiscountPrice(int value) {
            return value * 10 / 100;
        }
    }, TWENTY_PERCENTAGE {
        @Override
        protected int applyDiscountPrice(int value) {
            return value * 20 / 100;
        }
    };

    protected abstract int applyDiscountPrice(int value);

}
