package com.mosinsa.order.ui.request;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import lombok.Value;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Value
public class CreateOrderRequest {

    String customerId;
    String couponId;
    List<MyOrderProduct> myOrderProducts = new ArrayList<>();
    String zipcode;
    String address1;
    String address2;
    String shippingMessage;
    String receiverName;
    String receiverPhoneNumber;

    public CreateOrderRequest(String customerId, String couponId, List<MyOrderProduct> myOrderProducts,
                              String zipcode, String address1, String address2, String shippingMessage,
                              String receiverName, String receiverPhoneNumber) {
        this.customerId = customerId;
        this.couponId = couponId;
        this.myOrderProducts.addAll(myOrderProducts);
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.shippingMessage = shippingMessage;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        valid();
    }

    private void valid() {
        if (!StringUtils.hasText(customerId)) {
            throw new OrderException(OrderError.VALIDATION_ERROR);
        }
        if (myOrderProducts.isEmpty()) {
            throw new OrderException(OrderError.VALIDATION_ERROR);
        }
    }

}
