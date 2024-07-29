package com.mosinsa.order.command.domain;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AuditingEntity {

	@EmbeddedId
	private OrderId id;

	private String customerId;

	private String couponId;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "total_price")
	private Money totalPrice;

	@Embedded
	private ShippingInfo shippingInfo;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "order_product",
			joinColumns = @JoinColumn(name = "order_id")
	)
	private final List<OrderProduct> orderProducts = new ArrayList<>();

	public static Order create(OrderId orderId, String customerId, String couponId, List<OrderProduct> orderProducts, ShippingInfo shippingInfo, int totalPrice) {
		Order order = new Order();
		order.id = orderId;
		order.useCoupon(couponId);
		order.setCustomerId(customerId);
		order.status = OrderStatus.PAYMENT_WAITING;
		order.addOrderProducts(orderProducts);
		order.setShippingInfo(shippingInfo);
		order.setTotalPrice(Money.of(totalPrice));
		return order;
	}

	public static Order create(String customerId, String couponId, List<OrderProduct> orderProducts, ShippingInfo shippingInfo, int totalPrice) {
		Order order = new Order();
		order.id = OrderId.newId();
		order.useCoupon(couponId);
		order.setCustomerId(customerId);
		order.status = OrderStatus.PAYMENT_WAITING;
		order.addOrderProducts(orderProducts);
		order.setShippingInfo(shippingInfo);
		order.setTotalPrice(Money.of(totalPrice));
		return order;
	}

	private void setTotalPrice(Money totalPrice) {
		this.totalPrice = totalPrice;
	}

	private void useCoupon(String couponId) {
		this.couponId = couponId;
	}

	private void setShippingInfo(ShippingInfo shippingInfo){
		verifyNotYetShipped();
		this.shippingInfo = shippingInfo;
	}
	private void setCustomerId(String customerId) {
		if (!StringUtils.hasText(customerId)){
			throw new OrderException(OrderError.NO_ORDER_CUSTOMER);
		}
		this.customerId = customerId;
	}

	private void addOrderProducts(List<OrderProduct> orderProducts) {
		verifyAtLeastOneOrderProducts(orderProducts);
		this.orderProducts.addAll(orderProducts);
	}

	public void cancelOrder() {
		verifyAlreadyCanceled();
		verifyNotYetShipped();
		this.status = OrderStatus.CANCELED;
	}

	private void verifyAlreadyCanceled() {
		if(status == OrderStatus.CANCELED){
			throw new AlreadyCanceledException("이미 취소된 주문입니다.");
		}
	}

	private void verifyAtLeastOneOrderProducts(List<OrderProduct> orderProducts) {
		if (orderProducts == null || orderProducts.isEmpty()){
			throw new OrderException(OrderError.ORDER_AT_LEAST_ONE_OR_MORE_PRODUCTS);
		}
	}

	private void verifyNotYetShipped() {
		if(!isNotYetShipped()){
			throw new AlreadyShippedException("이미 배송중입니다.");
		}
	}

	private boolean isNotYetShipped() {
		return status == OrderStatus.PAYMENT_WAITING || status == OrderStatus.PREPARING;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return Objects.equals(id, order.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
