package com.mosinsa.gateway.jwt;

public enum TokenUtilKey {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken")
    ;

    final String value;
    TokenUtilKey(String value){
        this.value = value;
    }

    public String key(){
        return this.value;
    }
}
