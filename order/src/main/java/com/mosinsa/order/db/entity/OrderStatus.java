package com.mosinsa.order.db.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    // 주문 생성, 취소, 주문요청 성공, 주문 완료(배송, 결제 로직필요)
    CREATE, CANCEL, REQUEST_SUCCESS, COMPLETE
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
