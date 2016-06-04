package com.salestock.core;

import org.junit.Before;
import org.junit.Test;

import com.salestock.saleass.core.product.Product;
import com.salestock.saleass.core.sale.Sale;

import static org.assertj.core.api.Assertions.*;
import static com.salestock.common.IterableCommon.stream;

public class SaleTests {
    @Test
    public void sale_equality() {
        Sale sameSale = new Sale();
        sameSale.setId(1);
        Sale differentSale = new Sale();
        differentSale.setId(2);

        assertThat(saleToTest).isEqualTo(sameSale);
        assertThat(saleToTest).isNotEqualTo(differentSale);
    }

    @Test
    public void add_2_momogi_and_1_pepsi_should_totaled_6_000() {
        saleToTest.addLineItem(momogi, 2);
        saleToTest.addLineItem(pepsi);
        assertThat(saleToTest.getTotal().intValue()).isEqualTo(6_000);
    }

    @Test
    public void add_ajam_and_pepsi_should_totaled_55_000() {
        saleToTest.addLineItem(ajam);
        saleToTest.addLineItem(pepsi);
        assertThat(saleToTest.getTotal().intValue()).isEqualTo(55_000);
    }

    @Test
    public void changing_product_price_shouldnot_change_sale_total() {
        saleToTest.addLineItem(momogi);
        assertThat(saleToTest.getTotal().intValue()).isEqualTo(500);
        momogi.setUnitPrice(300);
        assertThat(saleToTest.getTotal().intValue()).isEqualTo(500);
    }

    @Test
    public void add_line_item_should_automatically_be_grouped_by_product() {
        saleToTest.addLineItem(momogi, 2);
        saleToTest.addLineItem(pepsi);
        assertThat(stream(saleToTest.getLineItems()).count()).isEqualTo(2);

        saleToTest.addLineItem(momogi);
        assertThat(stream(saleToTest.getLineItems()).count()).isEqualTo(2);
        assertThat(saleToTest.getLineItems()).extracting("quantity").contains(3, 1);
    }

    @Before
    public void init() {
        momogi = new Product();
        momogi.setUnitPrice(500);

        pepsi = new Product();
        pepsi.setUnitPrice(5_000);

        ajam = new Product();
        ajam.setUnitPrice(50_000);

        saleToTest = new Sale();
        saleToTest.setId(1);
    }

    Product momogi;
    Product pepsi;
    Product ajam;
    Sale saleToTest;
}
