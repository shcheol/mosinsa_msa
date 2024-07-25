package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiverTest {


    @Test
    void equalsAndHashCode() {
        String name = "name";
        String phoneNumber = "010-0000-0000";
        ReceiverDto dto = new ReceiverDto(name, phoneNumber);
        Receiver actual = Receiver.of(dto);
        Receiver expect = Receiver.of(dto);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual).isEqualTo(actual).isEqualTo(expect).hasSameHashCodeAs(expect)
                .isNotEqualTo(null).isNotEqualTo(new TestClass());

        Receiver otherReceiver = Receiver.of(new ReceiverDto("you", ""));
        assertThat(actual).isNotEqualTo(otherReceiver).doesNotHaveSameHashCodeAs(otherReceiver);
        Receiver otherReceiver2 = Receiver.of(new ReceiverDto("name", ""));
        assertThat(actual).isNotEqualTo(otherReceiver2).doesNotHaveSameHashCodeAs(otherReceiver2);
    }

}