package com.salestock.ui.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salestock.core.product.Product;
import com.salestock.core.product.ProductRepo;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductRepo productRepo;

    @Autowired
    public ProductController(ProductRepo productrepo) {
        this.productRepo = productrepo;
    }

    @RequestMapping("/{id}")
    public Product getById(@PathVariable("id") int id) {
        return productRepo.getOne(id);
    }
}
