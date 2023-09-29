package com.mosinsa.order.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.db.dto.OrderDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonConverter {

	private static final ObjectMapper om = new ObjectMapper();

	public static OrderDto readValueToOrderDto(String kafkaMessage) {
		log.info("convertStringToOrderDto: {}", kafkaMessage);

		try{
			return om.readValue(kafkaMessage, OrderDto.class);
		}catch (JsonProcessingException e){
			throw new IllegalArgumentException(e);
		}

	}

	public static String writeOrderDtoAsString(OrderDto orderDto) {
		log.info("writeValueAsString: {}", orderDto);

		try {
			return om.writeValueAsString(orderDto);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
