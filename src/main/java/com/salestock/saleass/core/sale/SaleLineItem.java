package com.salestock.saleass.core.sale;

import java.math.BigDecimal;

import com.salestock.saleass.core.product.Product;

public class SaleLineItem {
    private int quantity;
    private BigDecimal unitPrice;
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
        this.quantity = quantity;
        this.unitPrice = product.getUnitPrice();
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
