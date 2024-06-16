package com.mosinsa.customer.ui.filter;

public enum HeaderConst {

    CUSTOMER_INFO("Customer-Info"),
    ACCESS_TOKEN("Access-Token"),
    REFRESH_TOKEN("Refresh-Token");
    private final String value;

    HeaderConst(String value) {
        this.value = value;
    }

    public String key(){
        return this.value;
    }

}
