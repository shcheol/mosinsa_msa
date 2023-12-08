package com.mosinsa.product.ui.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GlobalResponse<T> {

	private String result;

	private T response;

	private String message;
}
