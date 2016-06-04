package com.salestock.core;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import com.salestock.core.product.Product;
import com.salestock.core.sale.Sale;

public class SaleTests {
    @Test
    public void add_2_momogi_and_1_pepsi_should_totaled_6_000() {
        Sale sale = new Sale();
        sale.addLineItem(momogi, 2);
        sale.addLineItem(pepsi);
        assertThat(sale.getTotal().intValue()).isEqualTo(6_000);
    }

    @Test
    public void add_ajam_and_pepsi_should_totaled_55_000() {
        Sale sale = new Sale();
        sale.addLineItem(ajam);
        sale.addLineItem(pepsi);
        assertThat(sale.getTotal().intValue()).isEqualTo(55_000);
    }

    @Before
    public void init() {
        momogi = new Product();
        momogi.setUnitPrice(500);

        pepsi = new Product();
        pepsi.setUnitPrice(5_000);

        ajam = new Product();
        ajam.setUnitPrice(50_000);
    }

    Product momogi;
    Product pepsi;
    Product ajam;
}
