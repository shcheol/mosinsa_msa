package com.mosinsa.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AuditingEntity {

    @EmbeddedId
    private OrderId id;

    private String customerId;

    private Money totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    private void calculateTotalPrice(){
        orderProducts.stream().map(op -> op.getP)
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void changeOrderStatus(OrderStatus status) {
        this.status = status;
    }

    public void addOrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public static Order createOrder(String customerId, List<OrderProduct> orderProducts) {

        Assert.isTrue(orderProducts.size() >= 1,"주문 상품은 1개 이상 필요합니다.");

        Order order = new Order();

        order.setCustomerId(customerId);

        order.changeOrderStatus(OrderStatus.CREATE);

        for (OrderProduct op : orderProducts) {
            order.addOrderProducts(op);
        }

        return order;
    }

    public void cancelOrder() {

        this.changeOrderStatus(OrderStatus.CANCEL);

    }

}
