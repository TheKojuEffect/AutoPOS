package com.kapilkoju.autopos.trade.purchase.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import com.kapilkoju.autopos.trade.purchase.service.PurchaseService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping(PurchaseLineApi.baseUrl)
class PurchaseLineApi(private val purchaseService: PurchaseService) {

    @GetMapping("/{purchaseLineId}")
    @Timed
    fun getPurchaseLine(@PathVariable("purchaseId") purchaseId: Long,
                        @PathVariable("purchaseLineId") purchaseLine: PurchaseLine)
            : ResponseEntity<PurchaseLine> {

        return if (purchaseLine.purchase.getId() == purchaseId) {
            ResponseEntity.ok(purchaseLine)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    @Timed
    fun createPurchaseLine(@PathVariable("purchaseId") purchase: Purchase,
                           @RequestBody @Valid purchaseLine: PurchaseLine)
            : ResponseEntity<PurchaseLine> {

        val addedPurchaseLine = purchaseService.addPurchaseLine(purchase, purchaseLine)
        return ResponseEntity
                .created(URI("/api/purchases/${purchase.getId()}/lines/${addedPurchaseLine.getId()}"))
                .body(addedPurchaseLine)
    }

    @PutMapping("/{purchaseLineId}")
    @Timed
    fun updatePurchaseLine(@PathVariable("purchaseId") purchase: Purchase,
                           @PathVariable("purchaseLineId") purchaseLineId: Long,
                           @RequestBody @Valid purchaseLine: PurchaseLine): ResponseEntity<PurchaseLine> {

        purchaseLine.setId(purchaseLineId)
        val updatedPurchaseLine = purchaseService.updatePurchaseLine(purchase, purchaseLine)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("purchaseLine", updatedPurchaseLine.getId().toString()))
                .body(updatedPurchaseLine)
    }

    @DeleteMapping("/{purchaseLineId}")
    @Timed
    fun deletePurchaseLine(@PathVariable("purchaseId") purchaseId: Long,
                           @PathVariable("purchaseLineId") purchaseLine: PurchaseLine): ResponseEntity<Void> {

        return if (purchaseLine.purchase.getId() == purchaseId) {
            purchaseService.deletePurchaseLine(purchaseLine)
            val headers = HeaderUtil.createEntityDeletionAlert("purchaseLine", purchaseLine.getId().toString())
            ResponseEntity(headers, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    companion object {
        const val baseUrl = "/api/purchases/{purchaseId}/lines"
    }
}
