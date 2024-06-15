package com.mosinsa.gateway.jwt;

public enum TokenMapEnums {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken")
    ;

    final String value;
    TokenMapEnums(String value){
        this.value = value;
    }

    public String key(){
        return this.value;
    }
}
