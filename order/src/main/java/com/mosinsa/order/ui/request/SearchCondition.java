package com.mosinsa.order.ui.request;

import com.mosinsa.order.command.domain.OrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@NoArgsConstructor
public class SearchCondition {

	private String customerId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private OrderStatus status;

	public void setStartDate(String startDate) {
		this.startDate = LocalDate.parse(startDate,DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
	}

	public void setEndDate(String endDate) {
		this.endDate = LocalDate.parse(endDate,DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(23,59,59);
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
