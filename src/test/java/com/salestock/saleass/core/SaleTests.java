package com.salestock.saleass.core;

import org.junit.Before;
import org.junit.Test;

import com.salestock.saleass.core.product.Product;
import com.salestock.saleass.core.sale.Sale;

import static org.assertj.core.api.Assertions.*;
import static com.salestock.common.core.IterableCommon.stream;

public class SaleTests {
    @Test
    public void sale_equality() {
        Sale sameSale = new Sale();
        sameSale.setId(1);
        Sale differentSale = new Sale();
        differentSale.setId(2);

        assertThat(saleSubject).isEqualTo(sameSale);
        assertThat(saleSubject).isNotEqualTo(differentSale);
    }

    @Test
    public void add_2_momogi_and_1_pepsi_should_totaled_6_000() {
        saleSubject.addLineItem(momogi, 2);
        saleSubject.addLineItem(pepsi);
        assertThat(saleSubject.getTotal().intValue()).isEqualTo(6_000);
    }

    @Test
    public void add_ajam_and_pepsi_should_totaled_55_000() {
        saleSubject.addLineItem(ajam);
        saleSubject.addLineItem(pepsi);
        assertThat(saleSubject.getTotal().intValue()).isEqualTo(55_000);
    }

    @Test
    public void changing_product_price_shouldnot_change_sale_total() {
        saleSubject.addLineItem(momogi);
        assertThat(saleSubject.getTotal().intValue()).isEqualTo(500);
        momogi.setUnitPrice(300);
        assertThat(saleSubject.getTotal().intValue()).isEqualTo(500);
    }

    @Test
    public void add_line_item_should_automatically_be_grouped_by_product() {
        saleSubject.addLineItem(momogi, 2);
        saleSubject.addLineItem(pepsi);
        assertThat(stream(saleSubject.getLineItems()).count()).isEqualTo(2);

        saleSubject.addLineItem(momogi);
        assertThat(stream(saleSubject.getLineItems()).count()).isEqualTo(2);
        assertThat(saleSubject.getLineItems()).extracting("quantity").contains(3, 1);
    }

    @Before
    public void init() {
        momogi = new Product();
        momogi.setId(1);
        momogi.setUnitPrice(500);
        pepsi = new Product();
        pepsi.setId(2);
        pepsi.setUnitPrice(5_000);
        ajam = new Product();
        ajam.setId(3);
        ajam.setUnitPrice(50_000);

        saleSubject = new Sale();
        saleSubject.setId(1);
    }

    Product momogi;
    Product pepsi;
    Product ajam;
    Sale saleSubject;
}
