package com.mosinsa.order.domain;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
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

	public void useCoupon(String couponId, String discountPolicy, boolean available) {
		if(!StringUtils.hasText(couponId)){
			return;
		}
		if (!available){
			throw new InvalidCouponException();
		}
		this.couponId = couponId;
		calculateTotalPriceWithCoupon(discountPolicy);
//		Events.raise(new OrderCreatedEvent(this.id.getId(), this.couponId));
	}

	private void calculateTotalPriceWithCoupon(String discountPolicy) {

		int discountPrice = DiscountPolicy.valueOf(discountPolicy).applyDiscountPrice(this.totalPrice.getValue());
		int discountedTotalPrice = this.totalPrice.getValue() - discountPrice;
		if(discountedTotalPrice < 0){
			throw new InvalidCouponException();
		}
		this.totalPrice = Money.of(discountedTotalPrice);
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
		calculateTotalPrice();
	}

	public void cancelOrder() {
		verifyNotYetShipped();
		this.status = OrderStatus.CANCELED;
//		Events.raise(new OrderCanceledEvent(this.id.getId(), this.couponId));
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Order order = (Order) o;
		return id != null && Objects.equals(id, order.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
