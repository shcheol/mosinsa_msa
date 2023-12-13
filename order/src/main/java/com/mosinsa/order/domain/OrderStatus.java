package com.mosinsa.order.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {

	// 결제 대기, 상품 준비, 출고, 배송중, 배송 완료, 취소
	PAYMENT_WAITING, PREPARING, SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED
	;

	public static boolean contains(OrderStatus status){

		if (status == null){
			return false;
		}

		for (OrderStatus value : values()) {
			if(value.name().equalsIgnoreCase(status.name())){
				return true;
			}
		}
		return false;
	}
}
