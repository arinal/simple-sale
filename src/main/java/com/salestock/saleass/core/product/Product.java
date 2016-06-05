package com.salestock.saleass.core.product;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.salestock.common.core.EntityBase;

@Entity
public class Product extends EntityBase {
    private String code;
    private String name;
    private BigDecimal unitPrice = BigDecimal.ZERO;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        setUnitPrice(BigDecimal.valueOf(unitPrice));
    }
}
