package com.mosinsa.order.common.argumentresolver;

public enum HeaderConst {

    ACCESS_TOKEN("Access-Token"),
    REFRESH_TOKEN("Refresh-Token"),
    CUSTOMER_INFO("Customer-Info")
    ;
    private String key;

    HeaderConst(String key){
        this.key = key;
    }

    public String key(){
        return key;
    }

}
