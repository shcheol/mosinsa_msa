package com.mosinsa.customer.common.jwt;

public enum TokenConst {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken")
    ;

    final String value;
    TokenConst(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
