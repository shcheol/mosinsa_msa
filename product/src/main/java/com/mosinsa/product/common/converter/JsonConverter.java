package com.mosinsa.product.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.application.dto.OrderDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
