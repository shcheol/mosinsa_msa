package com.mosinsa.coupon.ui.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GlobalResponse<T> implements BaseResponse {

	private String result;

	private T response;

	private String message;
}
