package io.koju.autopos.trade.sale.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.trade.sale.domain.Sale;
import io.koju.autopos.trade.sale.service.SaleService;
import io.koju.autopos.web.rest.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(SaleApi.API_SALES)
class SaleApi {

    static final String API_SALES = "/api/sales";

    private final SaleService saleService;

    @Autowired
    public SaleApi(SaleService saleService) {
        this.saleService = saleService;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Sale>> getAllSales(@RequestParam("status") Sale.Status status, Pageable pageable)
            throws URISyntaxException {
        Page<Sale> page = saleService.getSalesWithStatus(status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, API_SALES);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
