package com.kapilkoju.autopos.trade.purchase.api

import java.net.URI
import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.trade.purchase.domain.{Purchase, PurchaseLine}
import com.kapilkoju.autopos.trade.purchase.repo.PurchaseLineRepo
import com.kapilkoju.autopos.trade.purchase.service.PurchaseService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import com.kapilkoju.autopos.trade.purchase.domain.{Purchase, PurchaseLine}
import com.kapilkoju.autopos.trade.purchase.repo.PurchaseLineRepo
import com.kapilkoju.autopos.trade.purchase.service.PurchaseService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/api/purchases/{purchaseId}/lines"))
class PurchaseLineApi(private val purchaseService: PurchaseService,
                      private val purchaseLineRepo: PurchaseLineRepo) {

  @GetMapping(Array("/{purchaseLineId}"))
  @Timed
  def getPurchaseLine(@PathVariable("purchaseId") purchaseId: Long,
                  @PathVariable("purchaseLineId") purchaseLineOp: PurchaseLine): ResponseEntity[PurchaseLine] = {
    Option(purchaseLineOp) match {
      case Some(purchaseLine)
        if purchaseLine.getPurchase.getId == purchaseId => ResponseEntity.ok(purchaseLine)
      case _ => new ResponseEntity[PurchaseLine](HttpStatus.NOT_FOUND)
    }
  }


  @PostMapping
  @Timed
  def createPurchaseLine(@PathVariable("purchaseId") purchase: Purchase,
                     @RequestBody @Valid purchaseLine: PurchaseLine): ResponseEntity[PurchaseLine] = {

    val addedPurchaseLine = purchaseService.addPurchaseLine(purchase, purchaseLine)
    ResponseEntity
      .created(new URI(s"/api/purchases/${purchase.getId}/lines/${addedPurchaseLine.getId}"))
      .body(addedPurchaseLine)
  }

  @PutMapping(Array("/{purchaseLineId}"))
  @Timed
  def updatePurchaseLine(@PathVariable("purchaseId") purchase: Purchase,
                     @PathVariable("purchaseLineId") purchaseLineId: Long,
                     @RequestBody @Valid purchaseLine: PurchaseLine): ResponseEntity[PurchaseLine] = {

    purchaseLine.setId(purchaseLineId)
    val updatedPurchaseLine = purchaseService.updatePurchaseLine(purchase, purchaseLine)
    ResponseEntity.ok
      .headers(HeaderUtil.createEntityUpdateAlert("purchaseLine", updatedPurchaseLine.getId.toString))
      .body(updatedPurchaseLine)
  }

  @DeleteMapping(Array("/{purchaseLineId}"))
  @Timed
  def deletePurchaseLine(@PathVariable("purchaseId") purchaseId: Long,
                     @PathVariable("purchaseLineId") purchaseLineOp: PurchaseLine): ResponseEntity[Void] = {

    Option(purchaseLineOp) match {
      case Some(purchaseLine)
        if purchaseLine.getPurchase.getId == purchaseId => {

        purchaseService.deletePurchaseLine(purchaseLine)
        ResponseEntity.ok
          .headers(HeaderUtil.createEntityDeletionAlert("purchaseLine", purchaseLine.getId.toString)).build
      }
      case _ => new ResponseEntity[Void](HttpStatus.NOT_FOUND)
    }
  }
}
