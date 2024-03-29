package com.mosinsa.order.infra.feignclient;

import lombok.Getter;

@Getter
public enum HeaderConst {

    CUSTOMER_ID("CustomerId"),
    ACCESS_TOKEN("Access-Token"),
    REFRESH_TOKEN("Refresh-Token")
    ;
    private String name;

    HeaderConst(String name){
        this.name = name;
    }

}
