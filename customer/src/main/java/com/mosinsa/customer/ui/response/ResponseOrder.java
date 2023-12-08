package com.mosinsa.customer.ui.response;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ResponseOrder {

    private final List<ResponseOrderProduct> orderProducts = new ArrayList<>();
    private int totalPrice;
}
