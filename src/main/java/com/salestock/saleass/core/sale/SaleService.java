package com.salestock.saleass.core.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salestock.common.core.DomainServiceBase;

@Service
public class SaleService extends DomainServiceBase<Sale> {
    @Autowired
    public SaleService(SaleRepo saleRepo) {
        super(Sale.class, saleRepo);
    }

    public Sale create() {
        Sale sale = new Sale();
        sale.setCode(generateCode());
        return sale;
    }

    public Sale save(Sale sale) {
        if (sale.isTransient())
            sale.setCode(generateCode());
        return repository.save(sale);
    }

    private String generateCode() {
        return "S" + (repository.count() + 1);
    }
}
