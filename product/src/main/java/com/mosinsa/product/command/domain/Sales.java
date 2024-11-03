package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Sales extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Embedded
    private SalesPolicy salesPolicy;
    @Embedded
    private Discount discount;

    public static Sales of(SalesPolicy salesPolicy, Discount discount){
        Sales sales = new Sales();
        sales.salesPolicy = salesPolicy;
        sales.discount = discount;
        return sales;
    }

    protected void setProduct(Product product){
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
