package com.salestock.core.sale;

import java.math.BigDecimal;

import com.salestock.core.product.Product;

public class SaleLineItem {
    private Product product;
    private int quantity;

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public SaleLineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return product.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
