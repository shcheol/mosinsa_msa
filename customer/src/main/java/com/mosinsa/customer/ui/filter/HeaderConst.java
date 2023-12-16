package com.mosinsa.customer.ui.filter;

public enum HeaderConst {

    CUSTOMER_ID("CustomerId"),
    ACCESS_TOKEN("Access-Token"),
    REFRESH_TOKEN("Refresh-Token");
    private final String value;

    HeaderConst(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
