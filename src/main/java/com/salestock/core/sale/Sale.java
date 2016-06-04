package com.salestock.core.sale;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.salestock.core.product.Product;

public class Sale {

    private Set<SaleLineItem> lineItems;

    public Sale() {
        lineItems = new HashSet<SaleLineItem>();
    }

    public BigDecimal getTotal() {
        return lineItems.stream().map(sli -> sli.getSubtotal()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public void addLineItem(Product product, int quantity) {
        SaleLineItem sli = new SaleLineItem(product, quantity);
        lineItems.add(sli);
    }

    public void addLineItem(Product product) {
        addLineItem(product, 1);
    }
}
