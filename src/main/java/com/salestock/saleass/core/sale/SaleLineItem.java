package com.salestock.saleass.core.sale;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.salestock.common.core.EntityBase;
import com.salestock.saleass.core.product.Product;

@Embeddable
public class SaleLineItem {
    private int quantity;
    private BigDecimal unitPrice;

    @ManyToOne
    private Product product;

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public SaleLineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = (quantity > 0)? quantity : 0;
        this.unitPrice = product.getUnitPrice();
    }

    SaleLineItem() { }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public SaleLineItem addQuantity(int quantity) {
        return new SaleLineItem(product, this.quantity + quantity);
    }
}
