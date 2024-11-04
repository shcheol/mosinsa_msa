package com.mosinsa.product.infra.jpa;

import com.mosinsa.product.command.domain.SalesPolicyType;

import java.util.Set;

public record CategorySearchCondition(Set<String> ids, SalesPolicyType sales) {
}
