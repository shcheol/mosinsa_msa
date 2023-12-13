package com.mosinsa.customer.infra.feignclient;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseOrderProduct {

    private String name;
    private int price;
    private int orderCount;
}
