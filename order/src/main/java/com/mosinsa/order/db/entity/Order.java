package com.mosinsa.order.db.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
    private String customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
//        customer.getOrders().add(this);
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
