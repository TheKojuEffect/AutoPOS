package io.koju.autopos.trade.sale.web;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.web.View;
import io.koju.autopos.trade.sale.domain.SaleInvoice;
import io.koju.autopos.trade.sale.service.SaleInvoiceService;
import io.koju.autopos.web.rest.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(SaleInvoiceApi.API_SALE_INVOICES)
class SaleInvoiceApi {

    static final String API_SALE_INVOICES = "/api/sale_invoices";

    private final SaleInvoiceService saleInvoiceService;

    @Autowired
    public SaleInvoiceApi(SaleInvoiceService saleInvoiceService) {
        this.saleInvoiceService = saleInvoiceService;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SaleInvoice>> getAllSaleInvoices(Pageable pageable)
            throws URISyntaxException {
        Page<SaleInvoice> page = saleInvoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, API_SALE_INVOICES);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/pending", method = GET)
    @JsonView(View.Summary.class)
    @Timed
    public ResponseEntity<List<SaleInvoice>> getPendingSales(Pageable pageable) throws URISyntaxException {
        final Page<SaleInvoice> pendingSales = saleInvoiceService.getPendingSaleInvoices(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pendingSales, API_SALE_INVOICES + "/pending");
        return new ResponseEntity<>(pendingSales.getContent(), headers, HttpStatus.OK);
    }

}
