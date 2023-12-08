package com.mosinsa.customer.ui.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseOrderProduct {

    private String name;
    private int price;
    private int orderCount;
}
