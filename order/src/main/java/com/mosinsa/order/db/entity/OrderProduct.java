package com.mosinsa.order.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

@Entity
@Table(name = "order_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @Column(name = "order_product_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productId;

    private int orderCount;

    public void setOrder(Order order){
        this.order = order;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

    public void setOrderCount(int orderCount){
        this.orderCount = orderCount;
    }

    public static OrderProduct createOrderProduct(String productId, int requestCount) {

        Assert.isTrue(requestCount>0,"상품은 1개 이상 구매해야 됩니다.");

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.id = UUID.randomUUID().toString();
        orderProduct.setProductId(productId);
        orderProduct.setOrderCount(requestCount);

        return orderProduct;
    }

    public void cancelOrderProduct(){

        //todo
//        getProduct().addStock(orderCount);
    }

}
