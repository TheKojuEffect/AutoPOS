package com.kapilkoju.autopos.trade.purchase.api


import com.fasterxml.jackson.annotation.JsonView
import com.kapilkoju.autopos.kernel.json.Views
import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import com.kapilkoju.autopos.trade.purchase.dto.CreatePurchaseDto
import com.kapilkoju.autopos.trade.purchase.service.PurchaseService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping(PurchaseApi.baseUrl)
class PurchaseApi(private val purchaseService: PurchaseService) {

    @GetMapping

    @JsonView(Views.Summary::class)
    fun getAllPurchases(@RequestParam vat: Boolean = false, pageable: Pageable): ResponseEntity<List<Purchase>> {
        val page = purchaseService.getPurchases(vat, pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    @PostMapping

    fun createNewPurchase(@RequestBody createPurchaseDto: CreatePurchaseDto): ResponseEntity<Purchase> {
        val newPurchase = purchaseService.createNewPurchase(createPurchaseDto)
        return ResponseEntity
                .created(URI("/api/purchases/${newPurchase.getId()}"))
                .body(newPurchase)
    }

    @PutMapping("/{id}")

    fun updatePurchase(@PathVariable("id") id: Long, @RequestBody purchase: Purchase): ResponseEntity<Purchase> {
        purchase.setId(id)
        val updatedPurchase = purchaseService.updatePurchase(purchase)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("purchase", updatedPurchase.getId().toString()))
                .body(updatedPurchase)
    }

    @GetMapping("/{id}")

    fun getPurchase(@PathVariable("id") purchase: Purchase?): ResponseEntity<Purchase> {

        return if (purchase != null) {
            ResponseEntity.ok(purchase)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")

    fun deletePurchase(@PathVariable("id") purchase: Purchase?): ResponseEntity<Void> {

        return if (purchase != null) {
            purchaseService.deletePurchase(purchase)
            val headers = HeaderUtil.createEntityDeletionAlert("purchase", purchase.getId().toString())
            ResponseEntity(headers, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    companion object {
        const val baseUrl = "/api/purchases"
    }

}
