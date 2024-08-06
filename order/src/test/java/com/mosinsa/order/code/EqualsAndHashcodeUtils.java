package com.mosinsa.order.code;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsAndHashcodeUtils {

    public static boolean equalsAndHashcode(Object origin, Object same, Object protectedConstructor, Object notEquals, Object... notEqualsArr) {
        try {
            assertThat(origin).isEqualTo(origin).isEqualTo(same).hasSameHashCodeAs(same)
                    .isNotEqualTo(null).isNotEqualTo(new TestClass());
            assertThat(origin).isNotEqualTo(notEquals).doesNotHaveSameHashCodeAs(notEquals);
            assertThat(origin).isNotEqualTo(protectedConstructor).doesNotHaveSameHashCodeAs(protectedConstructor);

            if (notEqualsArr != null) {
                Arrays.stream(notEqualsArr)
                        .forEach(element -> assertThat(origin).isNotEqualTo(element).doesNotHaveSameHashCodeAs(element));
            }

        }catch (Exception ignore){
            return false;
        }
        return true;
    }
}
