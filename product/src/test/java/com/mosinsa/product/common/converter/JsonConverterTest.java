package com.mosinsa.product.common.converter;

import com.mosinsa.product.application.dto.OrderDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonConverterTest {

//	@Test
//	void readValueToOrderDto(){
//		String s = "{\"orderId\":null,\"customerId\":null,\"totalPrice\":0,\"status\":null,\"orderProducts\":[]}";
//		OrderDto orderDto = JsonConverter.readValueToOrderDto(s);
//		assertThat(orderDto).isEqualTo(new OrderDto());
//	}
//
//	@Test
//	void readValueToOrderDtoEx(){
//		assertThrows(IllegalArgumentException.class, () -> JsonConverter.readValueToOrderDto("{{}"));
//	}
//
//	@Test
//	void writeOrderDtoAsString(){
//		String s = JsonConverter.writeOrderDtoAsString(new OrderDto());
//
//		String answer = "{\"orderId\":null,\"customerId\":null,\"totalPrice\":0,\"status\":null,\"orderProducts\":[]}";
//		assertThat(s).isEqualTo(answer);
//	}
//
////	@Test
////	void writeOrderDtoAsStringEx(){
////		assertThrows(IllegalArgumentException.class, () -> JsonConverter.writeOrderDtoAsString(new TestOrderDto()));
////	}
//
//	static class TestOrderDto extends OrderDto{
//		private String test;
//
//	}
}