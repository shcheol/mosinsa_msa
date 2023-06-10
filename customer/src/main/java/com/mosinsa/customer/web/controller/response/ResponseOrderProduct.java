package com.mosinsa.customer.web.controller.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseOrderProduct {

    private String name;
    private int price;
    private int orderCount;
}
