package com.salestock.saleass.ui.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salestock.common.rest.RestControllerBase;
import com.salestock.saleass.core.sale.Sale;
import com.salestock.saleass.core.sale.SaleService;

@RestController @RequestMapping("/api/sale")
class SaleController extends RestControllerBase<Sale> {
    @Autowired
    SaleController(SaleService service) {
        super(service);
    }
}
