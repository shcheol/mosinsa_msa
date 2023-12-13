package com.mosinsa.order.domain;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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

	@Convert(converter = MoneyConverter.class)
	@Column(name = "total_price")
	private Money totalPrice;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "order_product",
			joinColumns = @JoinColumn(name = "order_id")
	)
	private final List<OrderProduct> orderProducts = new ArrayList<>();

	public static Order create(String customerId, List<OrderProduct> orderProducts) {

		Order order = new Order();
		order.id = OrderId.newId();
		order.setCustomerId(customerId);
		order.status = OrderStatus.PAYMENT_WAITING;
		order.addOrderProducts(orderProducts);
		return order;
	}


	public void setCustomerId(String customerId) {
		if (!StringUtils.hasText(customerId)){
			throw new OrderException(OrderError.NO_ORDER_CUSTOMER);
		}
		this.customerId = customerId;
	}

	public void addOrderProducts(List<OrderProduct> orderProducts) {
		verifyAtLeastOneOrderProducts(orderProducts);
		this.orderProducts.addAll(orderProducts);
		calculateTotalPrice();
	}

	public void cancelOrder() {
		verifyNotYetShipped();
		this.status = OrderStatus.CANCELED;
	}
	private void verifyAtLeastOneOrderProducts(List<OrderProduct> orderProducts) {
		if (orderProducts == null || orderProducts.isEmpty()){
			throw new OrderException(OrderError.ORDER_AT_LEAST_ONE_OR_MORE_PRODUCTS);
		}
	}
	private void calculateTotalPrice() {
		this.totalPrice = Money.of(orderProducts.stream().mapToInt(op -> op.getAmounts().getValue()).sum());
	}

	private void verifyNotYetShipped() {
		if(!isNotYetShipped()){
			throw new AlreadyShippedException();
		}

	}

	private boolean isNotYetShipped() {
		return status == OrderStatus.PAYMENT_WAITING || status == OrderStatus.PREPARING;
	}

}
