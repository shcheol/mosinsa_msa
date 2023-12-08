package com.mosinsa.product.ui.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    private String name;
    private int price;
    private int stock;
    private long likes=0;

}
