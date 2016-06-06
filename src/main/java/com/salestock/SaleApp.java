package com.salestock;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.salestock.saleass.core.product.Product;
import com.salestock.saleass.core.product.ProductRepo;
import com.salestock.saleass.core.sale.Sale;
import com.salestock.saleass.core.sale.SaleRepo;

@SpringBootApplication
public class SaleApp {
    public static void main(String[] args) {
        SpringApplication.run(SaleApp.class, args);
    }

    @Bean
    CommandLineRunner init(ProductRepo productRepo, SaleRepo saleRepo) {
        return evt -> {
            Product momogi = new Product();
            momogi.setCode("P01");
            momogi.setName("Momogi");
            momogi.setUnitPriceByLong(500);
            momogi = productRepo.save(momogi);

            Product pepsi = new Product();
            pepsi.setCode("P02");
            pepsi.setName("Pepsi");
            pepsi.setUnitPriceByLong(5000);
            pepsi = productRepo.save(pepsi);

            Product ayam = new Product();
            ayam.setCode("P03");
            ayam.setName("Ayam");
            ayam.setUnitPriceByLong(50_000);
            ayam = productRepo.save(momogi);

            Product xiao = new Product();
            xiao.setCode("P04");
            xiao.setName("Xiao Long Bao");
            xiao.setUnitPriceByLong(40_000);
            xiao = productRepo.save(xiao);

            Product maybach = new Product();
            maybach.setCode("P05");
            maybach.setName("Maybach");
            maybach.setUnitPriceByLong(9_000_000_000L);
            maybach = productRepo.save(maybach);

            Product windows = new Product();
            windows.setCode("P06");
            windows.setName("Windows 10");
            windows.setUnitPriceByLong(500_000);
            windows = productRepo.save(windows);

            Sale s01 = new Sale();
            s01.setCode("S01");
            s01.addLineItem(momogi, 2);
            s01.addLineItem(pepsi, 1);

            Sale s02 = new Sale();
            s02.setCode("S02");
            s02.addLineItem(ayam, 1);
            s02.addLineItem(pepsi, 1);
            s02.addLineItem(xiao, 1);

            Sale s03 = new Sale();
            s03.setCode("S03");
            s03.addLineItem(windows, 1);

            Sale s04 = new Sale();
            s04.setCode("S04");
            s04.addLineItem(windows, 2);
            s04.addLineItem(xiao, 2);

            saleRepo.save(Arrays.asList(s01, s02, s03, s04));
        };
    }
}
