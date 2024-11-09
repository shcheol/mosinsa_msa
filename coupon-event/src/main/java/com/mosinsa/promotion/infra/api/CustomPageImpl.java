package com.mosinsa.promotion.infra.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPageImpl<T> extends PageImpl<T> {

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public CustomPageImpl(@JsonProperty("content") List<T> content, @JsonProperty("number") int number,
						  @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements,
						  @JsonProperty("pageable") JsonNode pageable) {
		super(content, PageRequest.of(number, size), totalElements);
	}
}
