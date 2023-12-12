package com.mosinsa.order.ui.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private String customerId;
	private List<MyOrderProduct> myOrderProducts = new ArrayList<>();

    public OrderCreateRequest(String customerId, List<MyOrderProduct> myOrderProducts) {
        this.customerId = customerId;
        this.myOrderProducts.addAll(myOrderProducts);
    }

	@AllArgsConstructor
	@Getter
	public static class MyOrderProduct{
		String productId;
		Integer price;
		Integer quantity;
	}
}
