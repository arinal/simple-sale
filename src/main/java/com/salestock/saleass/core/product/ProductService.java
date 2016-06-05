package com.salestock.saleass.core.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salestock.common.core.DomainServiceBase;

@Service
public class ProductService extends DomainServiceBase<Product> {
    @Autowired
    public ProductService(ProductRepo productRepo) {
        super(Product.class, productRepo);
    }
}
