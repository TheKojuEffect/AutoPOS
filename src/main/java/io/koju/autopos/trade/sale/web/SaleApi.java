package io.koju.autopos.trade.sale.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = SaleApi.API_SALES)
public class SaleApi {

    static final String API_SALES = "/api/sales";
}
