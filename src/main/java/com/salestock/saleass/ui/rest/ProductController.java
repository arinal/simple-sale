package com.salestock.saleass.ui.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salestock.saleass.core.product.Product;
import com.salestock.saleass.core.product.ProductRepo;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired private ProductRepo productRepo;

    @RequestMapping("/{id}")
    public Product getById(@PathVariable("id") int id) {
        return productRepo.getOne(id);
    }
}
