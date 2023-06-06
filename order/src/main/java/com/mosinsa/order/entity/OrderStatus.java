package com.mosinsa.order.entity;

public enum OrderStatus {

    // 주문 생성, 취소, 주문요청 성공, 주문 완료(배송, 결제 로직필요)
    CREATE, CANCEL, REQUEST_SUCCESS, COMPLETE
}
