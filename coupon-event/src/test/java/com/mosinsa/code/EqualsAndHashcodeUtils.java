package com.mosinsa.code;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsAndHashcodeUtils {

    public static void equalsAndHashcode(Object origin, Object same, Object notEquals, Object protectedConstructor){
        assertThat(origin).isEqualTo(origin).isEqualTo(same).hasSameHashCodeAs(same)
                .isNotEqualTo(null).isNotEqualTo(new TestClass());

        assertThat(origin).isNotEqualTo(notEquals).doesNotHaveSameHashCodeAs(notEquals);

        assertThat(protectedConstructor.hashCode()).isZero();
    }
}
