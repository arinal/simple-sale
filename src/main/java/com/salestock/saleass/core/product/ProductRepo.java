package com.salestock.saleass.core.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.salestock.common.core.Repository;

public interface ProductRepo extends Repository<Product> {
    @Query("from Product p where p.code like ?1% or p.name like ?1%")
    Page<Product> findAll(String search, Pageable page);
}
