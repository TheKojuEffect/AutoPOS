package com.kapilkoju.autopos.trade.sale.api


import com.kapilkoju.autopos.trade.sale.domain.Sale
import com.kapilkoju.autopos.trade.sale.domain.SaleLine
import com.kapilkoju.autopos.trade.sale.service.SaleService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping(SaleLineApi.baseUrl)
class SaleLineApi(private val saleService: SaleService) {

    @GetMapping("/{saleLineId}")

    fun getSaleLine(@PathVariable("saleId") saleId: Long,
                    @PathVariable("saleLineId") saleLine: SaleLine): ResponseEntity<SaleLine> {
        return ResponseEntity.ok(saleLine)
    }

    @PostMapping

    fun createSaleLine(@PathVariable("saleId") sale: Sale,
                       @RequestBody @Valid saleLine: SaleLine): ResponseEntity<SaleLine> {

        val addedSaleLine = saleService.addSaleLine(sale, saleLine)
        return ResponseEntity
                .created(URI("/api/sales/${sale.getId()}/lines/${addedSaleLine.getId()}"))
                .body(addedSaleLine)
    }

    @PutMapping("/{saleLineId}")

    fun updateSaleLine(@PathVariable("saleId") sale: Sale,
                       @PathVariable("saleLineId") saleLineId: Long,
                       @RequestBody @Valid saleLine: SaleLine): ResponseEntity<SaleLine> {

        saleLine.setId(saleLineId)
        val updatedSaleLine = saleService.updateSaleLine(sale, saleLine)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("saleLine", updatedSaleLine.getId().toString()))
                .body(updatedSaleLine)
    }

    @DeleteMapping("/{saleLineId}")

    fun deleteSaleLine(@PathVariable("saleId") saleId: Long,
                       @PathVariable("saleLineId") saleLine: SaleLine): ResponseEntity<Void> {
        saleService.deleteSaleLine(saleLine)
        val headers = HeaderUtil.createEntityDeletionAlert("saleLine", saleLine.getId().toString())
        return ResponseEntity(headers, HttpStatus.OK)
    }

    companion object {
        const val baseUrl = "/api/sales/{saleId}/lines"
    }
}
