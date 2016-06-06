package com.salestock.saleass.core.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.salestock.common.core.Repository;

public interface SaleRepo extends Repository<Sale> {
    @Query("from Sale s where s.code like ?1%")
    Page<Sale> findAll(String search, Pageable page);
}
