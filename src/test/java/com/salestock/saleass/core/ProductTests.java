package com.salestock.saleass.core;

import org.junit.Test;

import com.salestock.saleass.core.product.Product;

import static org.assertj.core.api.Assertions.*;

public class ProductTests {
    @Test
    public void product_equality() {
        Product productSubject = new Product();
        productSubject.setId(1);

        Product sameProduct = new Product();
        sameProduct.setId(1);
        Product differentProduct = new Product();
        differentProduct.setId(2);

        assertThat(productSubject).isEqualTo(sameProduct);
        assertThat(productSubject).isNotEqualTo(differentProduct);
    }

}
