package com.shopping.mosinsa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void changeOrderStatus(OrderStatus status) {
        this.status = status;
    }

    public void addOrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public static Order createOrder(Customer customer, List<OrderProduct> orderProducts) {

        Assert.isTrue(orderProducts.size() >= 1,"주문 상품은 1개 이상 필요합니다.");

        Order order = new Order();

        order.setCustomer(customer);

        order.changeOrderStatus(OrderStatus.CREATE);

        for (OrderProduct op : orderProducts) {
            order.addOrderProducts(op);
        }

        return order;
    }

    public void cancelOrder() {

        this.changeOrderStatus(OrderStatus.CANCEL);
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.cancelOrderProduct();
        }
    }

    public int getTotalPrice(){
        return orderProducts.stream().mapToInt(OrderProduct::getTotalPrice).sum();
    }
}
