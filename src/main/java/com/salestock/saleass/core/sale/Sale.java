package com.salestock.saleass.core.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import com.salestock.common.core.EntityBase;
import com.salestock.saleass.core.product.Product;

public class Sale extends EntityBase {
    private String code;
    private LocalDateTime time;

    private HashSet<SaleLineItem> lineItems;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Iterable<SaleLineItem> getLineItems() {
        return lineItems;
    }

    public Sale() {
        time = LocalDateTime.now();
        lineItems = new HashSet<SaleLineItem>();
    }

    public BigDecimal getTotal() {
        return lineItems.stream()
            .map(SaleLineItem::getSubtotal)
            .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public Optional<SaleLineItem> getLineItem(Product product) {
        return lineItems.stream()
            .filter(sli -> sli.getProduct().equals(product))
            .findFirst();
    }

    public void addLineItem(Product product, int quantity) {
        SaleLineItem saleLineItem = getLineItem(product).orElseGet(() -> {
                SaleLineItem sli = new SaleLineItem(product, 0);
                lineItems.add(sli);
                return sli;
            });
        saleLineItem.addQuantity(quantity);
    }

    public void addLineItem(Product product) {
        addLineItem(product, 1);
    }
}