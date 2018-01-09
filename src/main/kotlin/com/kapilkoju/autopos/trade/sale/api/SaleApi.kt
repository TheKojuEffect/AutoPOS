package com.kapilkoju.autopos.trade.sale.api

import com.codahale.metrics.annotation.Timed
import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.kernel.json.Views
import com.kapilkoju.autopos.trade.sale.domain.Sale
import com.kapilkoju.autopos.trade.sale.service.SaleService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping(SaleApi.baseUrl)
class SaleApi(private val saleService: SaleService) {

    @GetMapping
    @Timed
    @JsonView(Views.Summary::class)
    fun getAllSales(@RequestParam("status") status: SaleStatus, pageable: Pageable): ResponseEntity<List<Sale>> {
        val page = saleService.getSalesWithStatus(status, pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    @PostMapping
    @Timed
    fun createNewSale(): ResponseEntity<Sale> {
        val newSale = saleService.createNewSale()
        return ResponseEntity
                .created(URI("/api/sales/${newSale.getId()}"))
                .body(newSale)
    }

    @PutMapping("/{id}")
    @Timed
    fun updateSale(@PathVariable("id") id: Long, @RequestBody sale: Sale)
            : ResponseEntity<Sale> {
        sale.setId(id)
        val updatedSale = saleService.updateSale(sale)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("sale", updatedSale.getId().toString()))
                .body(updatedSale)
    }

    @GetMapping("/{id}")
    @Timed
    fun getSale(@PathVariable("id") sale: Sale): ResponseEntity<Sale> {
        return ResponseEntity.ok(sale)
    }

    @DeleteMapping("/{id}")
    @Timed
    fun deleteSale(@PathVariable("id") sale: Sale)
            : ResponseEntity<Void> {
        saleService.deleteSale(sale)
        val headers = HeaderUtil.createEntityDeletionAlert("sale", sale.getId().toString())
        return ResponseEntity(headers, HttpStatus.OK)
    }

    companion object {
        const val baseUrl = "/api/sales"
    }

}
