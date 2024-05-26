package com.mosinsa.order.ui;

import lombok.Getter;

@Getter
public enum HeaderConst {

    ACCESS_TOKEN("Access-Token"),
    REFRESH_TOKEN("Refresh-Token")
    ;
    private String name;

    HeaderConst(String name){
        this.name = name;
    }

}
