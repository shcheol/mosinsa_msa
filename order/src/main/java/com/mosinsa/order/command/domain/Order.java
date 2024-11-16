package com.mosinsa.order.command.domain;

import com.mosinsa.common.ex.OrderError;
import com.mosinsa.common.ex.OrderException;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {

	@EmbeddedId
	private OrderId id;

	private String customerId;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "total_price")
	private Money totalPrice;

	@Embedded
	private ShippingInfo shippingInfo;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
	private final List<OrderProduct> orderProducts = new ArrayList<>();


	public static Order create(OrderId orderId, String customerId, int totalPrice, ShippingInfo shippingInfo, List<OrderProduct> orderProducts) {
		Order order = new Order();
		order.id = orderId;
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

	private void setShippingInfo(ShippingInfo shippingInfo) {
		verifyNotYetShipped();
		this.shippingInfo = shippingInfo;
	}

	private void setCustomerId(String customerId) {
		if (!StringUtils.hasText(customerId)) {
			throw new OrderException(OrderError.NO_ORDER_CUSTOMER);
		}
		this.customerId = customerId;
	}

	private void addOrderProducts(List<OrderProduct> orderProducts) {
		verifyAtLeastOneOrderProducts(orderProducts);
		orderProducts.forEach(orderProduct -> {
			orderProduct.setOrder(this);
			this.orderProducts.add(orderProduct);
		});
	}

	public void cancelOrder() {
		verifyAlreadyCanceled();
		verifyNotYetShipped();
		this.status = OrderStatus.CANCELED;
	}

	private void verifyAlreadyCanceled() {
		if (status == OrderStatus.CANCELED) {
			throw new AlreadyCanceledException("이미 취소된 주문입니다.");
		}
	}

	private void verifyAtLeastOneOrderProducts(List<OrderProduct> orderProducts) {
		if (orderProducts == null || orderProducts.isEmpty()) {
			throw new OrderException(OrderError.ORDER_AT_LEAST_ONE_OR_MORE_PRODUCTS);
		}
	}

	private void verifyNotYetShipped() {
		if (!isNotYetShipped()) {
			throw new AlreadyShippedException("이미 배송중입니다.");
		}
	}

	private boolean isNotYetShipped() {
		return status == OrderStatus.PAYMENT_WAITING || status == OrderStatus.PREPARING;
	}

	protected Order() {

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
