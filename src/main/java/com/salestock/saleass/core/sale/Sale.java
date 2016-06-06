package com.salestock.saleass.core.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import com.salestock.common.core.EntityBase;
import com.salestock.saleass.core.product.Product;

@Entity
public class Sale extends EntityBase {
    private String code;
    private LocalDateTime time;

    @ElementCollection
    private List<SaleLineItem> lineItems;

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
        lineItems = new ArrayList<>();
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
        if (quantity <= 0) return;
        SaleLineItem sli = getLineItem(product)
            .orElseGet(() -> new SaleLineItem(product, 0));
        lineItems.remove(sli);
        lineItems.add(sli.addQuantity(quantity));
    }

    public void addLineItem(Product product) {
        addLineItem(product, 1);
    }

    public void groupLineItems() {
        SaleLineItem[] cloned = new SaleLineItem[lineItems.size()];
        lineItems.toArray(cloned);
        lineItems.clear();
        for (SaleLineItem sli : cloned)
            addLineItem(sli.getProduct(), sli.getQuantity());
    }
}
